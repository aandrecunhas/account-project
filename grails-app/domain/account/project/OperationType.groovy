package account.project

enum OperationType {
    CREDIT_CASH("CreditCash", 1),
    CREDIT_INSTALLMENT("CreditInstallment", 2),
    WITHDRAWAL("Withdrawal", 0),
    PAYMENT("Payment", 0)

    String name
    Integer chargerOrder

    OperationType(String name, Integer chargeOrder) {
        this.name = name
        this.chargerOrder = chargeOrder
    }

}
