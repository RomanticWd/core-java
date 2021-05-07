package site.lgong.service.ast;

import site.lgong.service.IBisService;

public abstract class AbstractBisService<T, F> implements IBisService<T, F> {

    protected abstract boolean limit();

    @Override
    public String processA(T params) {
        return null;
    }

    @Override
    public String processB(F params) {
        return null;
    }
}
