package com.example.funky.workflow

import com.example.funky.contract.Funk
import net.corda.v5.application.flows.Flow
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.crypto.SecureHash

class FunkyWorkflow : Flow<SecureHash> {
    @Suspendable
    override fun call(): SecureHash {
        Funk(setOf("Funk1", "Funk2"))
        return SecureHash.randomSHA256()
    }
}
