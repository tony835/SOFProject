$().ready(function() {
		// validate signup form on keyup and submit
		$("#signupForm").validate({
			rules: {
				code: {
					required: true,
					minlength: 1
				},
				name: {
					required: true,
					minlength: 1
				},
				"responsable.login": {
					required: true,
					minlength: 1
				}
			},
			messages: {
				code: {
					required: "Renseigner le code.",
					minlength: "Le code doit avoir au moins 1 caractère."
				},
				name: {
					required: "Renseigner le nom.",
					minlength: "Le nom doit avoir au moins 1 caractère."
				},
				"responsable.login": {
					required: "Renseigner le nom du responsable.",
					minlength: "Le nom du responsable doit avoir au moins 1 caractère."
				}
			}
		});
	}
);