/**
 *
 */
package com.borneo.framework.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.borneo.framework.base.vo.LabelValueVO;

/**
 * @author seven.shi
 */
public class EConstant {

	public static List<LabelValueVO> getCreditTypes() {
		List<LabelValueVO> list = new ArrayList<LabelValueVO>();
		for (CreditType creditType : CreditType.values()) {
			LabelValueVO labelValueVO = new LabelValueVO();
			labelValueVO.setLabel(creditType.name());
			labelValueVO.setValue(creditType.ordinal());
			list.add(labelValueVO);
		}
		return list;
	}

	public static List<LabelValueVO> getCouponTypes() {
		List<LabelValueVO> list = new ArrayList<LabelValueVO>();
		for (CouponType usertpye : CouponType.values()) {
			LabelValueVO labelValueVO = new LabelValueVO();
			labelValueVO.setLabel(usertpye.name());
			labelValueVO.setValue(usertpye.ordinal());
			list.add(labelValueVO);
		}
		return list;
	}

	public static List<LabelValueVO> getUserTpyes() {
		List<LabelValueVO> list = new ArrayList<LabelValueVO>();
		for (UserType usertpye : UserType.values()) {
			LabelValueVO labelValueVO = new LabelValueVO();
			labelValueVO.setLabel(usertpye.name());
			labelValueVO.setValue(usertpye.ordinal());
			list.add(labelValueVO);
		}
		return list;
	}

	public enum UserType {
		SystemUserAcct,CustomUserAcct
	}

	public static List<LabelValueVO> getRelocationStatuses() {
		List<LabelValueVO> list = new ArrayList<LabelValueVO>();
		for (RelocationStatus relocationStatus : RelocationStatus.values()) {
			LabelValueVO labelValueVO = new LabelValueVO();
			labelValueVO.setLabel(relocationStatus.name());
			labelValueVO.setValue(relocationStatus.ordinal());
			list.add(labelValueVO);
		}
		return list;
	}

	public enum RelocationStatus {
		Pending, Approved, Rejected
	}

	public static List<LabelValueVO> getPromotionTypes() {
		List<LabelValueVO> list = new ArrayList<LabelValueVO>();
		for (PromotionType promotionType : PromotionType.values()) {
			LabelValueVO labelValueVO = new LabelValueVO();
			labelValueVO.setLabel(promotionType.name());
			labelValueVO.setValue(promotionType.ordinal());
			list.add(labelValueVO);
		}
		return list;
	}

	public enum CouponType {
		Topup, Wash
	}

	public enum CouponStatus {
		Inactive, Active
	}

	public enum PromotionType {
		TopUp, Wash
	}

	public enum DownloadStatus {
		Remote_File_Noexist, Local_Bigger_Remote, Download_From_Break_Success, Download_From_Break_Failed, Download_New_Success, Download_New_Failed
	}

	public enum UploadStatus {
		Create_Directory_Fail, Create_Directory_Success, File_Exits, Remote_Bigger_Local, Upload_From_Break_Failed, Upload_From_Break_Success, Delete_Remote_Faild, Upload_New_File_Success, Upload_New_File_Failed
	}

	public enum JsonResult {
		SUCCESS, FAIL
	}

	public enum ErrorCode {
		E_403, E_404, E_500
	}

	public enum AuthSuffix {
		READ, CREATE, EDIT, DELETE
	}

	public enum CreditType {
		BONUS, TOPUP, REFUND, PAY
	}

	public enum TransTypePrefix {
		PAYMENT("P"), WASH("W"), REFUND("R");

		private String prefix;

		private TransTypePrefix(String p) {
			prefix = p;
		}

		@Override
		public String toString() {
			return prefix;
		}
	}

	public enum ApiCode {
		success("00"), error("01");

		private String code;

		private ApiCode(String p) {
			code = p;
		}

		@Override
		public String toString() {
			return code;
		}
	}

	public enum SupervisorApiCode {
		success("0"), error("1");

		private String code;

		private SupervisorApiCode(String p) {
			code = p;
		}

		@Override
		public String toString() {
			return code;
		}
	}
	
	//Added by Jacky 24-Jul-2014 1924
	public enum CustomErrorCode {
		UnknowError("E000"), ObjectNull("E001");

		private String code;

		private CustomErrorCode(String p) {
			code = p;
		}

		@Override
		public String toString() {
			return code;
		}
	}
	
	// added by Nick 25-08-2014 1557
	public enum IpayRequeryResponseCode {
        Successful("00"), InvalidParameters("Invalid parameters"), RecordNotFound("Record not found"), IncorrectAmount("Incorrect amount"), PaymentFail("Payment fail"), M88Admin("M88Admin");

        private String code;

        private IpayRequeryResponseCode(String p) {
            code = p;
        }

        @Override
        public String toString() {
            return code;
        }
    }
	
	public enum IpayStatusCode {
	    Successful("1"), Fail("0");
	    
	    private String status;
	    
	    private IpayStatusCode(String status) {
	        this.status = status;
	    }

        @Override
        public String toString() {
            return status;
        }
	}
}
