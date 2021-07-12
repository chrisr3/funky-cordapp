package com.example.funky.workflow

import com.example.funky.contract.Funk
import net.corda.v5.application.flows.Flow
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.crypto.SecureHash

class FunkyWorkflow : Flow<SecureHash> {
    @Suspendable
    override fun call(): SecureHash {
        Funk(setOf("Funk1", "Funk2"))
        return SecureHash.create("SHA-256:ae62a19ed14ae8554245a47850a110d0dee0e084564ef70039bcd0cb2b4925e4")
    }
}
