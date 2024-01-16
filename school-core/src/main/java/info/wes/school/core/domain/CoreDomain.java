package info.wes.school.core.domain;

import java.io.Serializable;

public interface CoreDomain<T> extends Serializable {

	public abstract T getGenericKey();

	public abstract void setId(T id);

}