package info.wes.school.core.service;

public interface CoreService<REPOSITORY, DOMAIN> {

	DOMAIN findById(Long id);
	
	int save(DOMAIN domain);

	int add(DOMAIN domain);
	
	int set(DOMAIN domain);

	int remove(DOMAIN domain);

}