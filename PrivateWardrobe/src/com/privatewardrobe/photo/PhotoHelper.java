package com.privatewardrobe.photo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.privatewardrobe.activity.PhotoFilterActivity;
import com.privatewardrobe.common.FileManager;
import com.privatewardrobe.common.Utils;

public class PhotoHelper {
	public final static int GETFROM_CAPTURE = 1314;
	public final static int GETFROM_ALBUM = 1315;
	public final static int GETFROM_PROCESSING = 1316;

	final static int CAMERA_TAG = 1;
	final static int ALBUM_TAG = 2;

	private static int SIZE_SMALL = 400;

	private static int SIZE_LARGE = 1024;// 1024;

	private int photoTag;

	private Uri tempUri;

	private Activity activity;
	private Fragment fragment;
	private PhotoProcessListener listener;

	public PhotoHelper(Activity activity, PhotoProcessListener listener) {
		this.activity = activity;
		this.listener = listener;
	}

	public PhotoHelper(Fragment fragment,
			PhotoProcessListener listener) {
		this.fragment = fragment;
		this.listener = listener;
	}

	public void startCapture() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			File file_go = Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

			if (!file_go.exists()) {
				file_go.mkdir();
			}
			tempUri = Uri.withAppendedPath(Uri.fromFile(file_go),
					Utils.getPhotoFileName());
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
			startActivityForResult(intent, GETFROM_CAPTURE);
		} else {
			Toast.makeText(getContext(), "请先安装好sd卡", Toast.LENGTH_LONG).show();
		}
	}

	public void startAlbum() {
		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, GETFROM_ALBUM);
		// activity.startActivityForResult(intent, GETFROM_ALBUM);
	}

	public void process(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == GETFROM_CAPTURE) {
				photoTag = CAMERA_TAG;
				if (tempUri != null) {
					loadImage(tempUri);
				} else {
					Toast.makeText(getContext(), "图片获取失败", Toast.LENGTH_LONG)
							.show();
				}

			} else if (requestCode == GETFROM_ALBUM) {
				photoTag = ALBUM_TAG;
				tempUri = Uri.parse(data.getDataString());
				loadImage(tempUri);
			} else if (requestCode == GETFROM_PROCESSING) {
				String tempDir = FileManager.getTempDir();
				String fileName = Utils.getPhotoFileName();
				String uriStr = data.getStringExtra("uri");
				Uri largeUri = Uri.parse(uriStr);
				Bitmap largeBmp = BitmapFactory.decodeFile(largeUri.getPath());
				if (largeBmp != null) {
					Bitmap thumbnailBmp = extractThumbnail(largeBmp, SIZE_SMALL);
					String thumbnailPath = tempDir + File.separator
							+ "THUMBNAIL_" + fileName;
					Uri thumbnailUri;
					try {
						thumbnailUri = saveBitmap(thumbnailBmp, thumbnailPath);
						if (!thumbnailBmp.isRecycled()) {
							thumbnailBmp.recycle();
						}
						System.gc();
						if (listener != null) {
							listener.onComplete(tempUri, largeUri, thumbnailUri);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		}
	}

	private void loadImage(Uri uri) {
		Uri sourceUri = uri;
		Bitmap sourceBmp = null;
		if (photoTag == ALBUM_TAG) {
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = ((Activity) getContext()).managedQuery(uri, proj,
					null, null, null);
			int index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			String path = cursor.getString(index);
			sourceBmp = decodeSampledBitmapFromFile(path);
		} else {
			sourceBmp = decodeSampledBitmapFromFile(uri.getPath());
		}
		if (sourceBmp == null) {// 有可能从Utils.getBitmapFromUri会失败返回null
			Toast.makeText(getContext(), "获取图片失败", Toast.LENGTH_SHORT).show();
			return;
		}

		if ((sourceBmp.getWidth() < SIZE_SMALL)
				|| (sourceBmp.getHeight() < SIZE_SMALL)) {
			Toast.makeText(getContext(), "您选择的图片过小, 请上传大于400*400的照片",
					Toast.LENGTH_SHORT).show();
			return;
		}

		String tempDir = FileManager.getTempDir();
		String fileName = Utils.getPhotoFileName();
		if (!tempDir.equals("")) {
			try {
				String largePath = tempDir + File.separator + "LARGE_"
						+ fileName;
				Bitmap largeBmp;
				if (sourceBmp.getWidth() < SIZE_LARGE
						|| sourceBmp.getHeight() < SIZE_LARGE) {
					largeBmp = sourceBmp;
				} else {
					largeBmp = extractThumbnail(sourceBmp, SIZE_LARGE);
				}

				Uri largeUri = saveBitmap(largeBmp, largePath);
				if (!largeBmp.isRecycled()) {
					largeBmp.recycle();
				}
				System.gc();
				// 去旋转或者滤镜
				Intent intent = new Intent();
				intent.setClass(getContext(), PhotoFilterActivity.class);
				intent.putExtra(PhotoFilterActivity.URI, largeUri.toString());
				startActivityForResult(intent, GETFROM_PROCESSING);
				// activity.startActivityForResult(intent, GETFROM_PROCESSING);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private Uri saveBitmap(Bitmap bmp, String path) throws IOException {
		File file = new File(path);
		FileOutputStream out = new FileOutputStream(file);
		bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
		out.flush();
		out.close();
		return Uri.fromFile(file);
	}

	private Bitmap extractThumbnail(Bitmap source, int minLength) {

		int height = source.getHeight();
		int width = source.getWidth();

		if (height >= width) {
			height = source.getHeight() * minLength / source.getWidth();
			return ThumbnailUtils.extractThumbnail(source, minLength, height,
					ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		} else {
			width = source.getWidth() * minLength / source.getHeight();
			return ThumbnailUtils.extractThumbnail(source, width, minLength,
					ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		}

	}

	private void startActivityForResult(Intent intent, int code) {
		if (activity != null) {
			activity.startActivityForResult(intent, code);
		}
		if (fragment != null) {
			fragment.startActivityForResult(intent, code);
		}
	}

	private Context getContext() {
		Context context = null;
		if (activity != null) {
			context = activity;
		} else if (fragment != null) {
			context = fragment.getActivity();
		}
		return context;
	}

	public static int calculateInSampleSize(BitmapFactory.Options options) {
		// Raw height and width of image
		int height = options.outHeight;
		int width = options.outWidth;
		if (height < width) {
			width = height;
		}
		int inSampleSize = 1;

		while (width > 1024) {
			width = width / 2;
			inSampleSize *= 2;
		}

		return inSampleSize;
	}

	public static Bitmap decodeSampledBitmapFromFile(final String path) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	public interface PhotoProcessListener {

		void onComplete(Uri source, Uri large, Uri thumbnail);

	}
}
