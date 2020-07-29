package org.consenlabs.tokencore.foundation.crypto;

import com.fasterxml.jackson.annotation.JsonIgnore;

interface KDFParams {
	
	int DK_LEN = 32;

	int getDklen();

	String getSalt();

	@JsonIgnore
	void validate();
	
}
