package vn.com.dpm.common.constants;


public final class CacheConstants {

    public static final long TTL_1_DAY = 86400;
    public static final String DPM_PREFIX = "dpm-";

    public static class Type {
        public static final String CAFFEINE = "caffeineCacheManager";
        public static final String REDIS = "cacheManager";

        private Type() {
        }
    }

    public static class Name {
        public static final String CONFIGS = "configs";
        public static final String BO = "bo";
        public static final String PROCESS_INFO = "process-info";
        public static final String EXECUTION_SNAPSHOT = "snapshot";

        public static final String CANDIDATE_GROUPS = "candidate-groups";
        public static final String CANCEL_GROUPS = "cancel-groups";
        public static final String WATCHER_GROUPS = "watcher-groups";
        public static final String CANDIDATE_STARTER_GROUPS = "candidate-starter-groups";
        public static final String PROCDEF_BY_CANDIDATE_STARTER_GROUP = "list-procdef-by-candidate-and-starter-groups";
        public static final String CATEGORY_BY_STARTER_GROUP = "list-category-by-starter-groups";
        public static final String CATEGORY_BY_PARTICIPATED_GROUP = "list-category-by-participated-groups";
        public static final String CANDIDATE_USER_BY_TASK_KEY = "list-candidate-user-by-task-key";
        public static final String TASK_BY_TASK_KEY = "task-by-task-key";
        public static final String VARIABLES = "variables";
        public static final String TASK_INFO = "task-info";
        public static final String KAFKA_PARTITION_OFFSET = "kafka-topic-partition-offset";
        public static final String PROCESS_PERMISSION = "process-permissions";
        public static final String PROCESS_PERMISSION_CODE = "process-permission-codes";

        private Name() {
        }
    }


    public static class Key {

        public static final String CONFIG_DUE_DATE_TASK = "config-due-date-task";
        public static final String HOLIDAY_CACHE_KEY = "holiday-config";

        private Key() {
        }
    }

    public static class Ttl {

        public static final long TTL_14_DAYS_SNAPSHOT = 14L;

        private Ttl() {
        }
    }


    private CacheConstants() {
    }
}
