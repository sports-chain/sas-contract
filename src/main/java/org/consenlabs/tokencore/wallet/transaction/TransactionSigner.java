package org.consenlabs.tokencore.wallet.transaction;

import org.consenlabs.tokencore.wallet.Wallet;

public interface TransactionSigner {

	TxSignResult signTransaction(int chain_id, String password, Wallet wallet);

}
