//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yongchun.library.utils;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import com.yongchun.library.R.string;
import com.yongchun.library.model.LocalMedia;
import com.yongchun.library.model.LocalMediaFolder;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class LocalMediaLoader {
    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_VIDEO = 2;
    private static final String[] IMAGE_PROJECTION = new String[]{"_data", "_display_name", "date_added", "_id"};
    private static final String[] VIDEO_PROJECTION = new String[]{"_data", "_display_name", "date_added", "_id", "duration"};
    private int type = 1;
    private FragmentActivity activity;

    public LocalMediaLoader(FragmentActivity activity, int type) {
        this.activity = activity;
        this.type = type;
    }

    public void loadAllImage(final LocalMediaLoader.LocalMediaLoadListener imageLoadListener) {
        this.activity.getSupportLoaderManager().initLoader(this.type, (Bundle)null, new LoaderCallbacks<Cursor>() {
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                CursorLoader cursorLoader = null;
                if (id == 1) {
                    cursorLoader = new CursorLoader(LocalMediaLoader.this.activity, Media.EXTERNAL_CONTENT_URI, LocalMediaLoader.IMAGE_PROJECTION, "mime_type=? or mime_type=?", new String[]{"image/jpeg", "image/png"}, LocalMediaLoader.IMAGE_PROJECTION[2] + " DESC");
                } else if (id == 2) {
                    cursorLoader = new CursorLoader(LocalMediaLoader.this.activity, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI, LocalMediaLoader.VIDEO_PROJECTION, (String)null, (String[])null, LocalMediaLoader.VIDEO_PROJECTION[2] + " DESC");
                }

                return cursorLoader;
            }

            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                ArrayList<LocalMediaFolder> imageFolders = new ArrayList();
                LocalMediaFolder allImageFolder = new LocalMediaFolder();
                List<LocalMedia> allImages = new ArrayList();
                if (data != null) {
                    int count = data.getCount();
                    if (count > 0) {
                        data.moveToFirst();

                        do {
                            String path = data.getString(data.getColumnIndexOrThrow(LocalMediaLoader.IMAGE_PROJECTION[0]));
                            if (!TextUtils.isEmpty(path) && (new File(path)).exists()) {
                                data.getString(data.getColumnIndexOrThrow(LocalMediaLoader.IMAGE_PROJECTION[1]));
                                long dateTime = data.getLong(data.getColumnIndexOrThrow(LocalMediaLoader.IMAGE_PROJECTION[2]));
                                int duration = LocalMediaLoader.this.type == 2 ? data.getInt(data.getColumnIndexOrThrow(LocalMediaLoader.VIDEO_PROJECTION[4])) : 0;
                                LocalMedia image = new LocalMedia(path, dateTime, (long)duration);
                                LocalMediaFolder folder = LocalMediaLoader.this.getImageFolder(path, imageFolders);
                                Log.i("FolderName", folder.getName());
                                folder.getImages().add(image);
                                folder.setImageNum(folder.getImageNum() + 1);
                                allImages.add(image);
                                allImageFolder.setImageNum(allImageFolder.getImageNum() + 1);
                            }
                        } while(data.moveToNext());

                        allImageFolder.setFirstImagePath(((LocalMedia)allImages.get(0)).getPath());
                        allImageFolder.setName(LocalMediaLoader.this.activity.getString(string.all_image));
                        allImageFolder.setImages(allImages);
                        imageFolders.add(allImageFolder);
                        LocalMediaLoader.this.sortFolder(imageFolders);
                        imageLoadListener.loadComplete(imageFolders);
                    }
                }

            }

            public void onLoaderReset(Loader<Cursor> loader) {
            }
        });
    }

    private void sortFolder(List<LocalMediaFolder> imageFolders) {
        Collections.sort(imageFolders, new Comparator<LocalMediaFolder>() {
            public int compare(LocalMediaFolder lhs, LocalMediaFolder rhs) {
                if (lhs.getImages() != null && rhs.getImages() != null) {
                    int lsize = lhs.getImageNum();
                    int rsize = rhs.getImageNum();
                    return lsize == rsize ? 0 : (lsize < rsize ? 1 : -1);
                } else {
                    return 0;
                }
            }
        });
    }

    private LocalMediaFolder getImageFolder(String path, List<LocalMediaFolder> imageFolders) {
        File imageFile = new File(path);
        File folderFile = imageFile.getParentFile();
        Iterator var5 = imageFolders.iterator();

        LocalMediaFolder folder;
        do {
            if (!var5.hasNext()) {
                LocalMediaFolder newFolder = new LocalMediaFolder();
                newFolder.setName(folderFile.getName());
                newFolder.setPath(folderFile.getAbsolutePath());
                newFolder.setFirstImagePath(path);
                imageFolders.add(newFolder);
                return newFolder;
            }

            folder = (LocalMediaFolder)var5.next();
        } while(!folder.getName().equals(folderFile.getName()));

        return folder;
    }

    public interface LocalMediaLoadListener {
        void loadComplete(List<LocalMediaFolder> var1);
    }
}
