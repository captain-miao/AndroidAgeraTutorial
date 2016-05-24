# AndroidAgeraTutorial
Android Agera Example.

# Change Text Color
## Click Button Send Update Event

```
    //onClick Observable
    mObservable = new OnClickObservable() {
        @Override
        public void onClick(View view) {
            dispatchUpdate();
        }
    };
    //when click button, then dispatchUpdate()
    mBinding.setObservable(mObservable);
    android:onClick="@{observable::onClick}"
```

## Repository

```
    //text color Supplier
    Supplier<Integer> supplier = new Supplier<Integer>() {
        @NonNull
        @Override
        public Integer get() {
            return MockRandomData.getRandomColor();
        }
    };
    
    mRepository = Repositories.repositoryWithInitialValue(0)
            .observe(mObservable)
            .onUpdatesPerLoop()
            .thenGetFrom(supplier)
            .compile();

    @Override
    protected void onResume() {
        super.onResume();
        mRepository.addUpdatable(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRepository.removeUpdatable(this);
    }
```

## setTxtColor

```
    @Override
    public void update() {
        mBinding.setTxtColor(mRepository.get());
    }
```
# MutableRepository
```
    private void setUpRepository() {
        mObservable = new OnClickObservable() {
            @Override
            public void onClick(View view) {
                mRepository.accept(MockRandomData.getRandomImage());
            }
        };

        mRepository = Repositories.mutableRepository(MockRandomData.getRandomImage());

        //initialization
        mRepository.accept(MockRandomData.getRandomImage());
    }

    @Override
    public void update() {
        String result = mRepository.get();
        mBinding.setImageUrl(result);
    }
```
# Load Image By Picasso
## Repository
```

    Supplier<String> imageUriSupplier = new Supplier<String>() {
        @NonNull
        @Override
        public String get() {
            return MockRandomData.getRandomImage();
        }
    };

    mRepository = Repositories.repositoryWithInitialValue(Result.<Bitmap>absent())
            .observe(mObservable)
            .onUpdatesPerLoop()
            .getFrom(imageUriSupplier)//image uri
            .goTo(networkExecutor)
            .thenTransform(new Function<String, Result<Bitmap>>() {
                @NonNull
                @Override
                public Result<Bitmap> apply(@NonNull String input) {
                    return new ImageSupplier(input).get();//image bitmap
                }
            })
            .compile();
```

# Load Data By Network
## Repository
```
    // Observable and Supplier
    mMutableRepository = Repositories.mutableRepository(mPagination);

    mLoadDataRepository = Repositories.repositoryWithInitialValue(Result.<ApiResult<GirlInfo>>absent())
            .observe(mMutableRepository)
            .onUpdatesPerLoop()
            .goTo(networkExecutor)
            .attemptGetFrom(new GirlsSupplier(mMutableRepository)).orSkip()
            .goLazy()
            .thenTransform(new Function<ApiResult<GirlInfo>, Result<ApiResult<GirlInfo>>>() {
                @NonNull
                @Override
                public Result<ApiResult<GirlInfo>> apply(@NonNull ApiResult<GirlInfo> input) {
                    return absentIfNull(input);
                }
            })
            .compile();
```
<br/>
