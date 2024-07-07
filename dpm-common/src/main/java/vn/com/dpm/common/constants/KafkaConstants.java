package vn.com.dpm.common.constants;

public final class KafkaConstants {

    public static class EventType {

        public static final String COMPLETE_CONTRACT_EVENT = "complete-contract-event";
        public static final String UPDATE_MFILE_TOKEN = "update-mfile-token";
        public static final String UPDATE_E_CONTRACT_V2_TOKEN = "update-e-contract-v2-token";
        public static final String UPDATE_I_CONTRACT_TOKEN = "update-i-contract-token";
        public static final String SERVICE_UNDER_MAINTAIN_EVENT = "service-under-maintain-event";
        public static final String RECEIVE_SPR = "receive-service-product-register";
        public static final String SPR_RESPONSE = "service-product-register-response";
        public static final String RECEIVE_SETTING_ACCOUNT_SPR = "receive-setup-account-dcash";
        public static final String RECEIVE_VERIFY_CUST = "receive-verify-customer-request";
        public static final String RECEIVE_VERIFY_CUST_V2_OA = "receive-verify-customer-oa-request";
        public static final String RECEIVE_BOND_EVENT = "receive-bond-event";
        public static final String RECEIVE_BOND_TPRL_EVENT = "BONDDEAL_STATUS";
        public static final String REJECTED_BOND_DEAL_EVENT = "REJECTED_CROSS_ORDER_EVENT";
        public static final String DPM_VEN_EVENT = "push-message-external";
        public static final String DPM_RELOAD_CONFIG = "dpm-reload-config";
        public static final String DPM_SCHEDULE_JOB = "dpm-schedule-job";
        public static final String RECEIVE_SETTING_ACCOUNT = "receive-setting-account-request";
        public static final String RECEIVE_ADJUST_INFO = "receive-adjust-info-request";
        public static final String RECEIVE_ADJUST_INFO_V2_RESPONSE = "receive-adjust-info-response";
        public static final String RECEIVE_PACKAGE_REGISTRATION = "receive-package-register";
        public static final String PACKAGE_REGISTRATION_RESPONSE = "service-package-register-response";
        public static final String DPM_NOTIFICATION = "dpm_notification";
        public static final String SERVICE_PRODUCT_DOC_UPLOAD = "service-product-doc-upload";
        public static final String DPM_VEN_EMAIL = "DPM_VEN_EMAIL";

        public static final String BO_TRANS = "BO_TRANS";
        public static final String FDS_TRANS = "FDS_TRANS";

        //region topic user_activity_event

        public static class NotifyEventType {
            public static final String REGISTER_AUTHORIZE = "REGISTER_AUTHORIZE";
            public static final String REGISTER_AUTHORIZE_PENDING_CONTRACT = "REGISTER_AUTHORIZE_PENDING_CONTRACT";
            public static final String REGISTER_AUTHORIZE_WAITING_SETUP = "REGISTER_AUTHORIZE_WAITING_SETUP";
            public static final String REGISTER_AUTHORIZE_COMPLETED = "REGISTER_AUTHORIZE_COMPLETED";
            public static final String REGISTER_AUTHORIZE_REJECTED = "REGISTER_AUTHORIZE_REJECTED";
            public static final String REGISTER_AUTHORIZE_EXPIRED = "REGISTER_AUTHORIZE_EXPIRED";

            public static final String CANCEL_AUTHORIZE = "CANCEL_AUTHORIZE";
            public static final String CANCEL_AUTHORIZE_PENDING_CONTRACT = "CANCEL_AUTHORIZE_PENDING_CONTRACT";
            public static final String CANCEL_AUTHORIZE_WAITING_SETUP = "CANCEL_AUTHORIZE_WAITING_SETUP";
            public static final String CANCEL_AUTHORIZE_COMPLETED = "CANCEL_AUTHORIZE_COMPLETED";
            public static final String CANCEL_AUTHORIZE_REJECTED = "CANCEL_AUTHORIZE_REJECTED";
            public static final String CANCEL_AUTHORIZE_EXPIRED = "CANCEL_AUTHORIZE_EXPIRED";

            public static final String BOND_TRANSACTION = "BOND_TRANSACTION";

            private NotifyEventType() {
            }
        }
        //endregion

        private EventType() {
        }
    }

    public static class ActionType {

        public static final String INIT_AUTHORIZE_CONTRACT = "step-init-complete-authorize-contract-task0";
        public static final String RECEIVE_BOND_REQUEST_FROM_JIRA = "receive-bond-request_from_jira";
        public static final String UPDATE_STATUS_REQUEST_ID_ECONTRACT_BOND = "update-status-request-id-econtract-bond";
        public static final String VEN_BOND_REJECTED = "BOND_REJECT";
        public static final String VEN_BOND_SUCCESSFULLY = "BOND_SUCCESSFULLY";
        public static final String NOTI_BOND_TO_VEN = "BOND_NOTI";
        public static final String RELOAD_TASK_ACTION_CONFIG = "reload-task-action-config";
        public static final String RELOAD_SCHEDULE_CONFIG = "reload-schedule-config";
        public static final String DPM_SCHEDULE_START_MANUAL = "dpm-schedule-job-start-manual";

        private ActionType() {
        }
    }

    public static class Consumer {
        public static final String MO_EVENT = "MO_EVENT";
        public static final String FDS_TRANS_EVENT = "FDS_TRANS_EVENT";

        private Consumer() {
        }
    }

    private KafkaConstants() {
    }

    public static class Mode {
        public static final String INHERIT = "inherit";

        private Mode() {
        }
    }
}
