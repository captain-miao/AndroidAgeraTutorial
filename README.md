# AndroidAgeraTutorial
Android Agera Example.

# Change Text Color
## Click Button Send Update Event</h5>

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
    //
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


<br/>
