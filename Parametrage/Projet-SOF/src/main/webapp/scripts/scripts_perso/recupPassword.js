$()
.ready(
		function() {
			$("#personnePasswordForm")
			.validate(
					{
						rules : {
							password : {
								required : true,
								minlength : 5,
								maxlength : 32
							},
							repeatPassword : {
								required : true,
								equalTo : "#password"
							}
						},

						messages : {
							password : {
								required : "Please enter a password",
								minlength : "Your username must consist of at least 5 characters",
								maxlength : "Your username must consist of at most 32 characters"
							},
							repeatPassword : {
								required : "Please enter the samme passwrd",
								equalTo : "Please enter the same password as above"
							}
						}
					});

		}
);