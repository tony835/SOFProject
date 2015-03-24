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
				}
			}
		});
	}
);