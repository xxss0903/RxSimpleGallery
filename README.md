## RxSimpleGallery
simple gallery developed by Rxjava
# usage
*select one photo

        RxChoosePhoto
                .with(this)
                .subscribe(new RxPhotoSubscriber<Photos>() {
                    @Override
                    protected void onEvent(Photos photos) throws Exception {
                        for (PhotoEntry p : photos.getPhotos()) {
                            Log.e("John", "MainActivity#onEvent" + " # " + p.getPath());
                        }
                    }
                }).openGallerySingle();
                
*select multi photos

     RxChoosePhoto
                .with(this)
                .subscribe(new RxPhotoSubscriber<Photos>() {
                    @Override
                    protected void onEvent(Photos photos) throws Exception {
                        for (PhotoEntry p : photos.getPhotos()) {
                            Log.e("John", "MainActivity#onEvent" + " # " + p.getPath());
                        }
                    }
                }).openGalleryMulti();
                
 *config 
 
         Configuration config = new Configuration.Builder()
                .setMaximum(6)
                .setBottomBarColor(Color.RED)
                .setTopBarColor(Color.RED)
                .hasCamera(true)
                .build();
        RxChoosePhoto.initConfig(config);
