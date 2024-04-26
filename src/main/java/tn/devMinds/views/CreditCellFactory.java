    package tn.devMinds.views;

    import tn.devMinds.controllers.admin.credit.AdminCreditCell;
    import tn.devMinds.controllers.admin.credit.AdminCreditCell.CreditDeletedListener;
    import tn.devMinds.models.Credit;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.control.ListCell;

    public class CreditCellFactory extends ListCell<Credit> {
        private final CreditDeletedListener listener;

        public CreditCellFactory(CreditDeletedListener listener) {
            this.listener = listener;
        }

        @Override
        protected void updateItem(Credit credit, boolean empty) {
            super.updateItem(credit, empty);
            if (empty){
                setText(null);
                setGraphic(null);
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/admin/credit/creditCell.fxml"));
                AdminCreditCell controller = new AdminCreditCell(credit, listener);
                loader.setController(controller);
                setText(null);
                try {
                    setGraphic(loader.load());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
