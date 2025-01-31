package com.luisdbb.tarea3AD2024base.view;

import java.util.ResourceBundle;

public enum FxmlView {
	USER {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("tituloGeneral.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/User.fxml";
		}
	},
	LOGIN {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("tituloGeneral.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/LoginNuevo.fxml";
		}
	},
	MenuPeregrino {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("tituloGeneral.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/MenuPeregrino.fxml";
		}
	},
	MenuResponsable {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("tituloGeneral.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/MenuResponsable.fxml";
		}
	},
	MenuAdministrador {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("tituloGeneral.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/MenuAdministrador.fxml";
		}
	},
	RegistroPeregrino {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("tituloGeneral.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/RegistroPeregrino.fxml";
		}
	},
	RegistroParada {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("tituloGeneral.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/RegistroParada.fxml";
		}
	},
	ExportarDatosParada {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("tituloGeneral.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/ExportarDatosParada.fxml";
		}
	},
	SellarCarnet {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("tituloGeneral.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/SellarCarnet.fxml";
		}
	};
		

	public abstract String getTitle();

	public abstract String getFxmlFile();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}
}
