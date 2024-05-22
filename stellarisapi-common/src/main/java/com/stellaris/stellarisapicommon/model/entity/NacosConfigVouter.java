package com.stellaris.stellarisapicommon.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class NacosConfigVouter {

    private List<Predicates> predicates;
    private String id;
    private List<Filters> filters;
    private String uri;
    private Integer order;

    @NoArgsConstructor
    @Data
    public static class Predicates {
        private Args args;
        private String name;

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public static class Args {
            private String pattern;
        }
    }

    @NoArgsConstructor
    @Data
    public static class Filters {
        private Args args;
        private String name;

        @NoArgsConstructor
        @Data
        public static class Args {
            private Integer parts;
        }
    }
}
