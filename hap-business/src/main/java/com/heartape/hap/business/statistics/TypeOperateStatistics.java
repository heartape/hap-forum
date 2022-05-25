package com.heartape.hap.business.statistics;

import lombok.Data;

/**
 * 资源操作统计，如关注等单次2状态不可重复操作,仅用于点赞
 */
public interface TypeOperateStatistics {

    enum TypeEnum {
        /** 积极的 */
        POSITIVE(1),
        /** 消极的 */
        NEGATIVE(-1);

        private final Integer typeCode;

        TypeEnum(Integer typeCode) {
            this.typeCode = typeCode;
        }

        public Integer getTypeCode() {
            return typeCode;
        }

        @Override
        public String toString() {
            return "{typeCode = " + typeCode + "}";
        }
    }

    @Data
    class TypeNumber {
        private Integer positive;
        private Integer negative;

        public TypeNumber(Integer positive, Integer negative) {
            this.positive = positive;
            this.negative = negative;
        }
    }

    /**
     * 积极操作数
     */
    int selectPositiveNumber(long resourceId);

    /**
     * 消极操作数
     */
    int selectNegativeNumber(long resourceId);

    /**
     * 记录积极操作
     */
    TypeNumber insert(long resourceId, long sponsorId, TypeEnum typeEnum);

    /**
     * 操作类型
     */
    TypeEnum type(long resourceId, long sponsorId);

    /**
     * 删除操作，用于资源删除
     */
    boolean delete(long resourceId, long sponsorId);

}
