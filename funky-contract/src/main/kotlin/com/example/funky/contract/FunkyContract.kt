package com.example.funky.contract

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import net.corda.v5.application.identity.AbstractParty
import net.corda.v5.persistence.MappedSchema
import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.ledger.contracts.Contract
import net.corda.v5.ledger.contracts.requireThat
import net.corda.v5.ledger.schemas.PersistentState
import net.corda.v5.ledger.schemas.QueryableState
import net.corda.v5.ledger.transactions.LedgerTransaction
import net.corda.v5.ledger.transactions.outputsOfType

class FunkyContract : Contract {
    override fun verify(tx: LedgerTransaction) {
        val funkyStates = tx.outputsOfType<FunkyState>()
        val allFunk = funkyStates.map(FunkyState::name).map(String::toUpperCase)
        requireThat {
            "Funk is unique!" using (setOf(allFunk).size == allFunk.size)
        }
    }

    class FunkyState(val name: String, override val participants: List<AbstractParty>) : QueryableState {
        override fun supportedSchemas(): Iterable<MappedSchema> = listOf(FunkySchema)
        override fun generateMappedObject(schema: MappedSchema): PersistentState {
            return when (schema) {
                is FunkySchema -> FunkySchema.PersistentFunkyState(name)
                else -> throw IllegalArgumentException("Unrecognised schema $schema")
            }
        }
    }
}

data class Funk(val names: Set<String>)

object FunkyBase

@CordaSerializable
object FunkySchema : MappedSchema(
    schemaFamily = FunkyBase::class.java,
    version = 1,
    mappedTypes = listOf(PersistentFunkyState::class.java)
) {
    override val migrationResource: String
        get() = "funky.changelog-master"

    @Entity
    @Table(name = "funky_states")
    class PersistentFunkyState(
        @Column(name = "name")
        var name: String,
    ) : PersistentState()
}
