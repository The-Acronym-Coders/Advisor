package com.teamacronymcoders.advisor.json;

public abstract class LazyLoadedType<T> {
    private T value;
    private final String valueName;

    protected LazyLoadedType(String valueName) {
        this.valueName = valueName;
    }

    protected abstract T loadValue();

    protected String getValueName() {
        return this.valueName;
    }

    public T get() {
        if (value == null) {
            this.value = loadValue();
        }
        return value;
    }
}
