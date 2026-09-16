<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Patient Registration</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        body {
            margin: 0;
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(135deg, #005bff, #ffffff);
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        .register-container {
            background: #fff;
            display: flex;
            border-radius: 16px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
            overflow: hidden;
            width: 1300px;
            max-width: 95%;
            height: 90vh;
        }

        /* Right Panel (Image Carousel) */
        .right-panel {
            flex: 1;
            position: relative;
            overflow: hidden;
            color: #fff;
            display: flex;
            flex-direction: column;
            justify-content: flex-end;
            align-items: flex-start;
            text-align: left;
        }

        .carousel {
            position: absolute;
            top: 0; left: 0;
            width: 100%; height: 100%;
            z-index: 0;
        }

        .carousel img {
            position: absolute;
            width: 100%; height: 100%;
            object-fit: cover;
            opacity: 0;
            transition: opacity 1s ease-in-out;
        }

        .carousel img.active {
            opacity: 1;
        }

        .overlay {
            position: absolute;
            top: 0; left: 0;
            width: 100%; height: 100%;
            background: linear-gradient(135deg, rgba(106,17,203,0.46), rgba(37,117,252,0.44));
            z-index: 1;
        }

        .right-content {
            position: relative;
            z-index: 2;
            padding: 60px 40px;
        }

        .right-content h2 {
            font-size: 32px;
            font-weight: 700;
            margin-bottom: 15px;
        }

        .h2head {
            color: #ffffff;
        }

        .right-content span {
            color: #ffeb3b;
        }

        .right-content p {
            font-size: 14px;
            opacity: 0.9;
        }

        /* Left Panel (Form Section) */
        .left-panel {
            flex: 1.3;
            padding: 40px 50px;
            overflow-y: auto;
        }

        h3.section-title {
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 10px;
            color: #333;
            border-left: 4px solid #2575fc;
            padding-left: 10px;
        }

        .card {
            background: #f9fafc;
            border-radius: 12px;
            padding: 20px 25px;
            margin-bottom: 25px;
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
        }

        .form-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
            gap: 15px;
        }

        label {
            font-size: 13px;
            font-weight: 500;
            color: #555;
        }

        .required::after {
            content: " *";
            color: #e74c3c;
        }

        input, select, textarea {
            width: 90%;
            padding: 10px 12px;
            border-radius: 8px;
            border: 1px solid #ccc;
            margin-top: 6px;
            outline: none;
            transition: 0.3s;
            background-color: #fff;
            appearance: none;
        }

        input:focus, select:focus, textarea:focus {
            border-color: #2575fc;
            box-shadow: 0 0 5px rgba(37,117,252,0.3);
        }

        select {
            background-image: url("data:image/svg+xml;utf8,<svg fill='%232575fc' height='24' viewBox='0 0 24 24' width='24' xmlns='http://www.w3.org/2000/svg'><path d='M7 10l5 5 5-5z'/></svg>");
            background-repeat: no-repeat;
            background-position: right 12px center;
            background-size: 16px;
            padding-right: 32px;
        }

        textarea {
            width: 100%;
            resize: vertical;
        }

        .register-btn {
            width: 100%;
            padding: 14px;
            border: none;
            border-radius: 8px;
            background: linear-gradient(135deg, #2575fc, #6a11cb);
            color: #fff;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: 0.3s;
        }

        .register-btn:hover {
            opacity: 0.9;
        }

        .alert {
            padding: 15px 20px;
            border-radius: 8px;
            margin-bottom: 25px;
            font-weight: 500;
        }

        .alert-error {
            background-color: rgba(231, 76, 60, 0.1);
            border: 2px solid #e74c3c;
            color: #e74c3c;
        }

        .alert-success {
            background-color: rgba(39, 174, 96, 0.1);
            border: 2px solid #27ae60;
            color: #27ae60;
        }

        .password-strength {
            margin-top: 5px;
            font-size: 12px;
        }

        .strength-weak { color: #e74c3c; }
        .strength-medium { color: #f39c12; }
        .strength-strong { color: #27ae60; }

        .form-footer {
            text-align: center;
            margin-top: 20px;
        }

        .form-footer a {
            color: #2575fc;
            text-decoration: none;
            font-weight: 500;
        }

        .form-footer a:hover {
            text-decoration: underline;
        }

        @media (max-width: 900px) {
            .register-container {
                flex-direction: column-reverse;
                height: auto;
            }
            .right-panel {
                height: 250px;
            }
            .left-panel {
                padding: 20px;
            }
        }
    </style>
</head>
<body>
<div class="register-container">
    <!-- Left Panel -->
    <div class="left-panel">
        <h2 style="text-align:center; font-weight:800; color:#2575fc; margin-bottom:20px;">Patient Registration</h2>

        <form th:action="@{/patient/register}" method="post" th:object="${patient}">
            <!-- Personal Info -->
            <div class="card">
                <h3 class="section-title">Personal Information</h3>
                <div class="form-grid">
                    <div>
                        <label for="fullName" class="required">Full Name</label>
                        <input type="text" id="fullName" name="fullName" th:field="*{fullName}" required placeholder="Enter your full name">
                    </div>
                    <div>
                        <label for="nicPassport" class="required">NIC / Passport No.</label>
                        <input type="text" id="nicPassport" name="nicPassport" th:field="*{nicPassport}" required placeholder="Enter NIC or Passport number">
                    </div>
                    <div>
                        <label for="dateOfBirth" class="required">Date of Birth</label>
                        <input type="date" id="dateOfBirth" name="dateOfBirth" th:field="*{dateOfBirth}" required>
                    </div>
                    <div>
                        <label for="gender" class="required">Gender</label>
                        <select id="gender" name="gender" th:field="*{gender}" required>
                            <option value="">Select Gender</option>
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                            <option value="Other">Other</option>
                        </select>
                    </div>
                    <div>
                        <label for="maritalStatus">Marital Status</label>
                        <select id="maritalStatus" name="maritalStatus" th:field="*{maritalStatus}">
                            <option value="">Select Marital Status</option>
                            <option value="Single">Single</option>
                            <option value="Married">Married</option>
                            <option value="Divorced">Divorced</option>
                            <option value="Widowed">Widowed</option>
                        </select>
                    </div>
                </div>
            </div>

            <!-- Contact Info -->
            <div class="card">
                <h3 class="section-title">Contact Information</h3>
                <div class="form-grid">
                    <div>
                        <label for="address">Address</label>
                        <textarea id="address" name="address" th:field="*{address}" rows="3" placeholder="Enter your permanent address"></textarea>
                    </div>
                    <div>
                        <label for="contactNumber" class="required">Contact Number</label>
                        <input type="tel" id="contactNumber" name="contactNumber" th:field="*{contactNumber}" required placeholder="Enter mobile number">
                    </div>
                    <div>
                        <label for="email" class="required">Email Address</label>
                        <input type="email" id="email" name="email" th:field="*{email}" required placeholder="Enter email address">
                    </div>
                </div>
            </div>

            <!-- Medical Info -->
            <div class="card">
                <h3 class="section-title">Medical Details</h3>
                <div class="form-grid">
                    <div>
                        <label for="bloodGroup">Blood Group</label>
                        <select id="bloodGroup" name="bloodGroup" th:field="*{bloodGroup}">
                            <option value="">Select Blood Group</option>
                            <option value="A+">A+</option>
                            <option value="A-">A-</option>
                            <option value="B+">B+</option>
                            <option value="B-">B-</option>
                            <option value="AB+">AB+</option>
                            <option value="AB-">AB-</option>
                            <option value="O+">O+</option>
                            <option value="O-">O-</option>
                        </select>
                    </div>
                    <div>
                        <label for="allergies">Allergies</label>
                        <textarea id="allergies" name="allergies" th:field="*{allergies}" rows="2" placeholder="List any known allergies"></textarea>
                    </div>
                    <div>
                        <label for="chronicConditions">Chronic Conditions</label>
                        <textarea id="chronicConditions" name="chronicConditions" th:field="*{chronicConditions}" rows="2" placeholder="List any chronic conditions"></textarea>
                    </div>
                    <div>
                        <label for="currentMedications">Current Medications</label>
                        <textarea id="currentMedications" name="currentMedications" th:field="*{currentMedications}" rows="2" placeholder="List current medications and dosages"></textarea>
                    </div>
                    <div>
                        <label for="insuranceProvider">Insurance Provider</label>
                        <input type="text" id="insuranceProvider" name="insuranceProvider" th:field="*{insuranceProvider}" placeholder="Insurance company name">
                    </div>
                    <div>
                        <label for="policyNumber">Policy Number</label>
                        <input type="text" id="policyNumber" name="policyNumber" th:field="*{policyNumber}" placeholder="Insurance policy number">
                    </div>
                    <div>
                        <label for="medicalHistory">Medical History</label>
                        <textarea id="medicalHistory" name="medicalHistory" th:field="*{medicalHistory}" rows="3" placeholder="Additional medical history or notes"></textarea>
                    </div>
                </div>
            </div>

            <!-- Emergency Contact -->
            <div class="card">
                <h3 class="section-title">Emergency Contact</h3>
                <div class="form-grid">
                    <div>
                        <label for="emergencyContactName">Emergency Contact Name</label>
                        <input type="text" id="emergencyContactName" name="emergencyContactName" th:field="*{emergencyContactName}" placeholder="Full name of emergency contact">
                    </div>
                    <div>
                        <label for="emergencyContactNumber">Emergency Contact Number</label>
                        <input type="tel" id="emergencyContactNumber" name="emergencyContactNumber" th:field="*{emergencyContactNumber}" placeholder="Mobile number of emergency contact">
                    </div>
                    <div>
                        <label for="emergencyContactRelationship">Relationship to Patient</label>
                        <select id="emergencyContactRelationship" name="emergencyContactRelationship" th:field="*{emergencyContactRelationship}">
                            <option value="">Select Relationship</option>
                            <option value="Spouse">Spouse</option>
                            <option value="Parent">Parent</option>
                            <option value="Child">Child</option>
                            <option value="Sibling">Sibling</option>
                            <option value="Friend">Friend</option>
                            <option value="Other">Other</option>
                        </select>
                    </div>
                </div>
            </div>

            <!-- Account Security -->
            <div class="card">
                <h3 class="section-title">Account Security</h3>
                <div class="form-grid">
                    <div>
                        <label for="username" class="required">Username</label>
                        <input type="text" id="username" name="username" th:field="*{username}" required placeholder="Choose a unique username">
                    </div>
                    <div>
                        <label for="password" class="required">Password</label>
                        <input type="password" id="password" name="password" th:field="*{password}" required placeholder="Create a strong password">
                        <div class="password-strength" id="passwordStrength"></div>
                    </div>
                    <div>
                        <label for="confirmPassword" class="required">Confirm Password</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" required placeholder="Re-enter your password">
                        <div class="password-strength" id="passwordMatch"></div>
                    </div>
                    <div>
                        <label for="securityQuestion">Security Question</label>
                        <select id="securityQuestion" name="securityQuestion" th:field="*{securityQuestion}">
                            <option value="">Select a security question</option>
                            <option value="What was your first pet's name?">What was your first pet's name?</option>
                            <option value="What is your mother's maiden name?">What is your mother's maiden name?</option>
                            <option value="What city were you born in?">What city were you born in?</option>
                            <option value="What was your first school name?">What was your first school name?</option>
                            <option value="What is your favorite book?">What is your favorite book?</option>
                        </select>
                    </div>
                    <div>
                        <label for="securityAnswer">Security Answer</label>
                        <input type="text" id="securityAnswer" name="securityAnswer" th:field="*{securityAnswer}" placeholder="Answer to security question">
                    </div>
                </div>
            </div>

            <!-- Error/Success Messages -->
            <div th:if="${error}" class="alert alert-error" th:text="${error}"></div>
            <div th:if="${success}" class="alert alert-success" th:text="${success}"></div>

            <button type="submit" class="register-btn">Create Patient Account</button>
        </form>

        <div class="form-footer">
            <p>Already have an account? <a href="/patient/login">Login here</a></p>
        </div>
    </div>

    <!-- Right Panel -->
    <div class="right-panel">
        <div class="carousel">
            <img src="https://images.unsplash.com/photo-1584467735871-8e85353a8413?auto=format&fit=crop&w=800&q=80" class="active" alt="Healthcare 1">
            <img src="https://images.unsplash.com/photo-1574706473454-a3b3da3fc5b5?auto=format&fit=crop&w=800&q=80" alt="Healthcare 2">
            <img src="https://images.unsplash.com/photo-1526256262350-7da7584cf5eb?auto=format&fit=crop&w=800&q=80" alt="Healthcare 3">
        </div>
        <div class="overlay"></div>
        <div class="right-content">
            <h2 class="h2head">Join <span>Our Patient Portal</span></h2>
            <p>Register to manage appointments, view medical history, and access personalized care.</p>
        </div>
    </div>
</div>

<script>
    // Carousel Effect
    const imgs = document.querySelectorAll('.carousel img');
    let idx = 0;
    setInterval(() => {
        imgs[idx].classList.remove('active');
        idx = (idx + 1) % imgs.length;
        imgs[idx].classList.add('active');
    }, 4000);

    // Password strength indicator
    const passwordInput = document.getElementById('password');
    const confirmPasswordInput = document.getElementById('confirmPassword');
    const passwordStrength = document.getElementById('passwordStrength');
    const passwordMatch = document.getElementById('passwordMatch');

    passwordInput.addEventListener('input', function() {
        const password = this.value;
        let strength = 'Weak';
        let strengthClass = 'strength-weak';

        if (password.length >= 8) {
            if (password.match(/[a-z]/) && password.match(/[A-Z]/) && password.match(/[0-9]/) && password.match(/[^a-zA-Z0-9]/)) {
                strength = 'Strong';
                strengthClass = 'strength-strong';
            } else if (password.match(/[a-z]/) && password.match(/[A-Z]/) && password.match(/[0-9]/)) {
                strength = 'Medium';
                strengthClass = 'strength-medium';
            }
        }

        passwordStrength.textContent = `Password strength: ${strength}`;
        passwordStrength.className = `password-strength ${strengthClass}`;
    });

    confirmPasswordInput.addEventListener('input', function() {
        if (this.value !== passwordInput.value) {
            passwordMatch.textContent = 'Passwords do not match';
            passwordMatch.className = 'password-strength strength-weak';
        } else {
            passwordMatch.textContent = 'Passwords match';
            passwordMatch.className = 'password-strength strength-strong';
        }
    });

    // Calculate age from date of birth
    document.getElementById('dateOfBirth').addEventListener('change', function() {
        const dob = new Date(this.value);
        const today = new Date();
        let age = today.getFullYear() - dob.getFullYear();
        const monthDiff = today.getMonth() - dob.getMonth();

        if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < dob.getDate())) {
            age--;
        }

        if (age < 0) {
            alert('Please enter a valid date of birth');
            this.value = '';
        }
    });
</script>
</body>
</html>