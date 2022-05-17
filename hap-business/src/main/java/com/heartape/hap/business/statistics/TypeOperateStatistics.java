package com.heartape.hap.business.statistics;

/**
 * 资源操作统计，如关注等单次2状态不可重复操作
 */
public interface TypeOperateStatistics {

    enum TypeEnum {
        /** 积极的 */
        POSITIVE(1),
        /** 消极的 */
        NEGATIVE(2),
        /** 未操作 */
        NULL(null);

        private final Integer typeCode;

        TypeEnum(Integer typeCode) {
            this.typeCode = typeCode;
        }

        public Integer getTypeCode() {
            return typeCode;
        }
    }

    /**
     * 资源积极操作是否被记录
     */
    boolean getPositiveOperate(long sourceId, long sponsorId);

    /**
     * 资源消极操作是否被记录
     */
    boolean getNegativeOperate(long sourceId, long sponsorId);

    /**
     * 资源积极操作数
     */
    int getPositiveOperateNumber(long sourceId);

    /**
     * 资源消极操作数
     */
    int getNegativeOperateNumber(long sourceId);

    /**
     * 记录资源受到的用户积极操作,返回true表示操作成功
     */
    boolean setPositiveOperate(long sourceId, long sponsorId);

    /**
     * 记录资源受到的用户消极操作,返回true表示操作成功
     */
    boolean setNegativeOperate(long sourceId, long sponsorId);

    /**
     * 资源操作类型
     */
    String getOperateType(long sourceId, long sponsorId);

    /**
     * 移除资源受到的用户操作,返回true表示操作成功
     */
    boolean removeOperate(long sourceId, long sponsorId);

    /**
     * 当前用户是否已经对目标资源进行操作
     */
    boolean getPeoplePositiveOperate(long sponsorId, long sourceId);

    /**
     * 当前用户是否已经对目标资源进行操作
     */
    boolean getPeopleNegativeOperate(long sponsorId, long sourceId);

    /**
     * 记录用户对目标资源的操作行为,返回true表示操作成功
     */
    boolean setPeoplePositiveOperate(long sponsorId, long sourceId);

    /**
     * 记录用户对目标资源的操作行为,返回true表示操作成功
     */
    boolean setPeopleNegativeOperate(long sponsorId, long sourceId);

    /**
     * 用户对目标资源操作类型
     */
    String getPeopleOperateType(long sponsorId, long sourceId);

    /**
     * 移除用户对目标资源的操作行为,返回true表示操作成功
     */
    boolean removePeopleOperate(long sponsorId, long sourceId);
}
