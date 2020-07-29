package org.consenlabs.tokencore.foundation.crypto;

import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.consenlabs.tokencore.foundation.utils.NumericUtil;
import org.consenlabs.tokencore.wallet.model.Messages;
import org.consenlabs.tokencore.wallet.model.TokenException;

public final class PBKDF2Crypto extends Crypto<PBKDF2Params> {
	static final String PBKDF2 = "pbkdf2";

	PBKDF2Crypto() {
		super();
		this.kdf = PBKDF2;
	}

	public static PBKDF2Crypto createPBKDF2Crypto() {
		PBKDF2Crypto crypto = new PBKDF2Crypto();
		byte[] salt = NumericUtil.generateRandomBytes(SALT_LENGTH);
		PBKDF2Params pbkdf2Params = PBKDF2Params.createPBKDF2Params();
		pbkdf2Params.setSalt(NumericUtil.bytesToHex(salt));
		crypto.kdfparams = pbkdf2Params;
		return crypto;
	}

	@Override
	public byte[] generateDerivedKey(byte[] password) {
		PBKDF2Params params = this.kdfparams;
		if (!PBKDF2Params.PRF.equals(params.getPrf())) {
			throw new TokenException(Messages.PRF_UNSUPPORTED);
		}

		PKCS5S2ParametersGenerator generator = new PKCS5S2ParametersGenerator(new SHA256Digest());
		generator.init(password, NumericUtil.hexToBytes(params.getSalt()), params.getC());
		return ((KeyParameter) generator.generateDerivedParameters(256)).getKey();
	}

}
