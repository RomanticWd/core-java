package site.lgong.service;

public interface IBisService<T, F> {

    /**
     * 业务A
     *
     * @param params
     * @return
     */
    String processA(T params);

    /**
     * 业务B
     *
     * @param params
     * @return
     */
    String processB(F params);

}
