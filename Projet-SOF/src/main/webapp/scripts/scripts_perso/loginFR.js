$().ready(function() {
		// validate signup form on keyup and submit
		$("#signupForm").validate({
			rules: {
				login: {
					required: true,
					minlength: 1
				},
				password: {
					required: true,
					minlength: 1
				}
			},
			messages: {
				login: {
					required: "Rentrer votre login.",
					minlength: "Votre login doit avoir au moins 1 caractère."
				},
				password: {
					required: "Merci de renseigner votre mot de passe.",
					minlength: "Votre mot de passe doit avoir au moins 1 caractère."
				}
			}
		});
	}
);