package com.blocadmin.core.utils;

import java.util.HashMap;
import java.util.Map;


public class Constants {
	public static final String DATE_TIME_FORMAT = "dd-MM-yyyy";
	
    public enum EXPENSE_TYPE {

        MONTHLY((short) 1, "Monthly"), YEARLY((short) 2, "Yearly"), BUILDING_MAINTAINANCE((short) 3, "Maintainance"), COMMON_FOND((short) 4, "Common"), OTHER((short)5, "Other");

        private short type;
        private String name;
        private static final Map<String, EXPENSE_TYPE> labelMap = new HashMap<String, Constants.EXPENSE_TYPE>();

        static {
            for (EXPENSE_TYPE e : values()) {
                labelMap.put(e.name, e);
            }
        }

        private EXPENSE_TYPE(short type, String name) {
            this.type = type;
            this.name = name;
        }

        public short getType() {
            return type;
        }

        public void setType(short type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static EXPENSE_TYPE valueOfLabel(String label) {
            return labelMap.get(label);
        }

        public static String getNameByCode(short code) {
            for (EXPENSE_TYPE e : EXPENSE_TYPE.values()) {
                if (code == e.type) {
                    return e.name;
                }
            }
            return null;
        }
    }
    
      public enum USER_TYPE {

        ADMIN((short) 1, "Admin"), OWNER((short) 2, "Owner"), ASSOCIATE((short) 3, "Associate"), EMPLOYEE((short) 4, "Employee"), OTHER((short)5, "Other");

        private short type;
        private String name;
        private static final Map<String, USER_TYPE> labelMap = new HashMap<String, Constants.USER_TYPE>();

        static {
            for (USER_TYPE u : values()) {
                labelMap.put(u.name, u);
            }
        }

        private USER_TYPE(short type, String name) {
            this.type = type;
            this.name = name;
        }

        public short getType() {
            return type;
        }

        public void setType(short type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static USER_TYPE valueOfLabel(String label) {
            return labelMap.get(label);
        }

        public static String getNameByCode(short code) {
            for (USER_TYPE u : USER_TYPE.values()) {
                if (code == u.type) {
                    return u.name;
                }
            }
            return null;
        }
    }
      
    public enum HOUSEHOLD_REQUEST_TYPE {

        COMPLAINT((short) 1, "Complaint"), DOC_RELEASE((short) 2, "Document"), OTHER((short)3, "Other");

        private short type;
        private String name;
        private static final Map<String, HOUSEHOLD_REQUEST_TYPE> labelMap = new HashMap<String, Constants.HOUSEHOLD_REQUEST_TYPE>();

        static {
            for (HOUSEHOLD_REQUEST_TYPE h : values()) {
                labelMap.put(h.name, h);
            }
        }

        private HOUSEHOLD_REQUEST_TYPE(short type, String name) {
            this.type = type;
            this.name = name;
        }

        public short getType() {
            return type;
        }

        public void setType(short type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static HOUSEHOLD_REQUEST_TYPE valueOfLabel(String label) {
            return labelMap.get(label);
        }

        public static String getNameByCode(short code) {
            for (HOUSEHOLD_REQUEST_TYPE h : HOUSEHOLD_REQUEST_TYPE.values()) {
                if (code == h.type) {
                    return h.name;
                }
            }
            return null;
        }
    }
       
    public enum BUDGET_TYPE {

        MONTHLY_HOUSEHOLD((short) 1, "Monthly"), REPAIRS((short) 2, "Repairs"), EMPLOYEE_SALARY((short) 3, "Employee"), WORKING_CAPITAL((short) 4, "Capital"), OTHER((short)5, "Other");

        private short type;
        private String name;
        private static final Map<String, BUDGET_TYPE> labelMap = new HashMap<String, Constants.BUDGET_TYPE>();

        static {
            for (BUDGET_TYPE b : values()) {
                labelMap.put(b.name, b);
            }
        }

        private BUDGET_TYPE(short type, String name) {
            this.type = type;
            this.name = name;
        }

        public short getType() {
            return type;
        }

        public void setType(short type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static BUDGET_TYPE valueOfLabel(String label) {
            return labelMap.get(label);
        }

        public static String getNameByCode(short code) {
            for (BUDGET_TYPE b : BUDGET_TYPE.values()) {
                if (code == b.type) {
                    return b.name;
                }
            }
            return null;
        }
    }
}
