$()
.ready(
		function() {
			// validate signup form on keyup and submit
			$("#signupForm")
			.validate(
					{
						rules : {
							logEmail : {
								required : true
							}
						},

						messages : {
							logEmail : {
								required : "Please enter your login or your email."
							}
						}
					});

		}
);