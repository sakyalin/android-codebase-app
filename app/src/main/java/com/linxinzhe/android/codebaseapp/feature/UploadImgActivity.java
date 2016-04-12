package com.linxinzhe.android.codebaseapp.feature;

import android.os.Bundle;
import android.view.View;

import com.linxinzhe.android.codebaseapp.R;
import com.linxinzhe.android.codebaseapp.base.BaseActivity;
import com.linxinzhe.android.codebaseapp.util.ImgUtil;
import com.linxinzhe.android.codebaseapp.view.CustomDialog;

import java.io.File;
import java.util.List;

import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

public class UploadImgActivity extends BaseActivity {

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            String selectedImgAbsPath = resultList.get(0).getPhotoPath();
            imgProcessor(selectedImgAbsPath);
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            showToast(errorMsg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_img);
    }

    @OnClick(R.id.btn_upload_img)
    public void upLoadImg(View view) {
        CustomDialog.selectWayDialog(this, getString(R.string.select_gallery), getString(R.string.select_camera), new CustomDialog.ButtonClick() {
            @Override
            public void act() {
                // gallery
                ImgUtil.openImgTaker(UploadImgActivity.this, ImgUtil.REQUEST_CODE_GALLERY, mOnHanlderResultCallback);
            }
        }, new CustomDialog.ButtonClick() {
            @Override
            public void act() {
                // camera
                ImgUtil.openImgTaker(UploadImgActivity.this, ImgUtil.REQUEST_CODE_CAMERA, mOnHanlderResultCallback);
            }
        }).show();
    }

    private void imgProcessor(String selectedImgAbsPath) {
        String imgName = "xxx.jpg";

        File imgFile = new File(selectedImgAbsPath);
        if (imgFile.exists()) {
            //rename to unique file name
            final File renamedImgFile = new File(imgFile.getParent(), imgName);
            boolean success = imgFile.renameTo(renamedImgFile);
            if (success) {
                // compress img file
                String renamedImgFileAbsolutePath = renamedImgFile.getAbsolutePath();
                ImgUtil.compressImage(renamedImgFileAbsolutePath);

                //start upload and show progress
//                try {
//                    final ProgressDialog uploadProgress = new ProgressDialog(this);
//                    uploadProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                    uploadProgress.setMax(100);

//                    String renamedImgFileName = renamedImgFile.getName();
//                    final AVFile uploadImg = AVFile.withAbsoluteLocalPath(renamedImgFileName, renamedImgFileAbsolutePath);
//                    uploadImg.saveInBackground(new SaveCallback() {
//                        @Override
//                        public void done(AVException e) {
//                            if (e == null) {
//                                mUploadImg = uploadImg;
//                                Picasso.with(PublishVideoActivity.this).load(renamedImgFile).fit().centerCrop().into(mImgBtnPublishImg);
//                                uploadProgress.dismiss();
//                            } else {
//                                showToast(e.getMessage());
//                            }
//                        }
//                    }, new ProgressCallback() {
//                        @Override
//                        public void done(Integer integer) {
//                            uploadProgress.setProgress(integer);
//                            uploadProgress.show();
//                        }
//                    });
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
            }
        }
    }
}
