package info.wes.school.biz.user.domain;

public enum UserMessage {
	
	SUCCESS(1, "성공 하였습니다."),
	
	SUCCESS_SAVE(2, "저장 되었습니다."),
	
	SUCCESS_REMOVE(3, "삭제 되었습니다."),
	
	INVALID_ACCESS(10, "접근 권한이 없습니다."),
	
	ILLEGAL_ARGUMENT(11, "올바르지 않은 파라미터 정보입니다."),
	
	BAD_REQUEST(12, "잘못된 요청정보 입니다."),
	
	FAIL(90, "실패 하였습니다."),
	
	UNKNOWN_ERROR(99, "알 수 없는 오류가 발생했습니다.");
	
	private final int code;
	
	private final String desc;
	
	UserMessage(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	// 값을 가져옴
	public int intValue() {
		return code;
	}
	
	// code값으로 UserMessage 타입 오브젝트를 가져오는 스태틱 메소드
	public static UserMessage valueOf(int code) {
		switch(code) {
			case 1 : return SUCCESS;
			case 10 : return INVALID_ACCESS;
			case 11 : return ILLEGAL_ARGUMENT;
			case 12 : return BAD_REQUEST;
			case 90 : return FAIL;
			case 99 : return UNKNOWN_ERROR;
			default : throw new AssertionError("Unknown code : " + code);
		}
	}

}
