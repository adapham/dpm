package vn.com.dpm.common.constants;

public final class SystemConstants {

    public static final String SYS_ADMIN = "SysAdmin";
    public static final String DPM_ADMIN = "dpmadmin";
    public static final String BPM_ADMIN = "bpmadmin"; //authenticated user call to 3rd service
    public static final String VIA_BPM = "BPM";
    public static final String VIA_DPM = "DPM";
    public static final String DPM_AUTO_BOT = "BOT";
    public static final String SERVICE_TASK = "serviceTask";
    public static final String USER_TASK = "userTask";
    public static final String TASK_DESCRIPTION = "taskDescription";
    public static final String PROC_INST_NUMBER_KEY = "proc_inst_number";
    public static final String PROC_DEF_KEY = "proc_def_key";
    public static final String MANUAL_START_USER = "manualStartUser";
    public static final String ASSIGNEE_UNASSIGNED = "unassigned";

    public static final String X_FORCE_SIGNATURE = "x-force-signature";
    public static final String DPM_SHARED_SIGNATURE = "4a53e9e10dc05a56537fc850c0121c33f078a418"; // signature ensure connect from API to Worker.

    private SystemConstants() {
    }

    public static class ActivityAction {
        public static final String CREATED = "CREATED";
        public static final String END = "END";
        public static final String AUTO = "serviceTask";

        private ActivityAction() {
        }
    }
}
