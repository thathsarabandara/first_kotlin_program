fun main() {
    while (true) {
        println("Welcome to your banking system")
        println("What type of account would you like to create?")
        println("1. Debit account")
        println("2. Credit account")
        println("3. Checking account")
        println("Choose an option (1, 2, or 3):")
        val accountType = readLine()?.toIntOrNull()

        val account = when (accountType) {
            1 -> {
                println("You have created a Debit account.")
                DebitAccount()
            }
            2 -> {
                println("You have created a Credit account.")
                CreditAccount()
            }
            3 -> {
                println("You have created a Checking account.")
                CheckingAccount()
            }
            else -> {
                println("Invalid Response! Please try again.")
                null
            }
        }

        if (account != null) {
            manageAccount(account)
        }
    }
}

fun manageAccount(account: BankAccount) {
    while (true) {
        println("\nChoose an operation: 1. Deposit  2. Withdraw  3. Exit")
        val operation = readLine()?.toIntOrNull()

        when (operation) {
            1 -> account.deposit()
            2 -> account.withdraw()
            3 -> {
                println("Exiting account management.")
                break
            }
            else -> println("Invalid option. Please try again.")
        }
    }
}

// Abstract class for BankAccount
abstract class BankAccount {
    protected var balance: Double = 0.0

    fun deposit() {
        println("Enter the amount to deposit:")
        val amount = readLine()?.toDoubleOrNull()
        if (amount != null && amount > 0) {
            balance += amount
            println("Deposited \$${"%.2f".format(amount)}. Current balance: \$${"%.2f".format(balance)}")
        } else {
            println("Invalid amount. Please enter a positive number.")
        }
    }

    fun withdraw() {
        println("Enter the amount to withdraw:")
        val amount = readLine()?.toDoubleOrNull()
        if (amount != null && amount > 0) {
            if (canWithdraw(amount)) {
                balance -= amount
                println("Withdrew \$${"%.2f".format(amount)}. Current balance: \$${"%.2f".format(balance)}")
            } else {
                println("Insufficient balance or withdrawal not permitted.")
            }
        } else {
            println("Invalid amount. Please enter a positive number.")
        }
    }

    protected abstract fun canWithdraw(amount: Double): Boolean
}

class CreditAccount : BankAccount() {
    private val creditLimit = 1000.0

    override fun canWithdraw(amount: Double): Boolean {
        return balance - amount >= -creditLimit
    }
}

class DebitAccount : BankAccount() {
    override fun canWithdraw(amount: Double): Boolean {
        return balance >= amount
    }
}

class CheckingAccount : BankAccount() {
    override fun canWithdraw(amount: Double): Boolean {
        return balance >= amount
    }
}
