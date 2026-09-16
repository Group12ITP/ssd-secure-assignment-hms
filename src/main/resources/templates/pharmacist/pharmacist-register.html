<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pharmacist Registration</title>
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

        .left-panel {
            flex: 1.3;
            padding: 40px 50px;
            overflow-y: auto;
        }

        h2 {
            text-align: center;
            font-weight: 800;
            color: #2575fc;
            margin-bottom: 25px;
        }

        .card {
            background: #f9fafc;
            border-radius: 12px;
            padding: 20px 25px;
            margin-bottom: 25px;
            box-shadow: 0 8px 25px rgba(0,0,0,0.15);
        }

        h3.section-title {
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 10px;
            color: #333;
            border-left: 4px solid #2575fc;
            padding-left: 10px;
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

        input, select {
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

        input:focus, select:focus {
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
            transition: opacity 1.2s ease-in-out;
        }

        .carousel img.active {
            opacity: 1;
        }

        .overlay {
            position: absolute;
            top:0;
            left:0;
            width: 100%;
            height: 100%;
            background: linear-gradient(135deg, rgba(37,117,252,0.45), rgba(106,17,203,0.45));
            z-index: 1;
        }

        .right-content {
            position: relative;
            z-index: 2;
            padding: 60px 40px;
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

        @media (max-width: 900px) {
            .register-container {
                flex-direction: column-reverse;
                height: auto;
            }
            .right-panel {
                height: 250px;
            }
        }
    </style>
</head>

<body>

<div class="register-container">
    <!-- LEFT PANEL -->
    <div class="left-panel">
        <h2>Pharmacist Registration</h2>

        <form th:action="@{/pharmacist/register}" th:object="${pharmacist}" method="post">

            <!-- Personal Info -->
            <div class="card">
                <h3 class="section-title">Personal Information</h3>
                <div class="form-grid">
                    <div><label>Full Name</label><input type="text" th:field="*{fullName}" required></div>
                    <div><label>Username</label><input type="text" th:field="*{username}" required></div>
                    <div><label>Password</label><input type="password" th:field="*{password}" required></div>
                    <div><label>Date of Birth</label><input type="date" th:field="*{dateOfBirth}" required></div>
                    <div>
                        <label>Gender</label>
                        <select th:field="*{gender}" required>
                            <option value="">Select Gender</option>
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                            <option value="Other">Other</option>
                        </select>
                    </div>
                    <div><label>NIC</label><input type="text" th:field="*{nic}" required></div>
                </div>
            </div>

            <!-- Contact Info -->
            <div class="card">
                <h3 class="section-title">Contact Information</h3>
                <div class="form-grid">
                    <div><label>Email</label><input type="email" th:field="*{email}" required></div>
                    <div><label>Contact Number</label><input type="text" th:field="*{contactNumber}" required></div>
                    <div><label>Address</label><input type="text" th:field="*{address}" required></div>
                </div>
            </div>

            <!-- Professional Info -->
            <div class="card">
                <h3 class="section-title">Professional Information</h3>
                <div class="form-grid">
                    <div><label>Qualification</label><input type="text" th:field="*{qualification}" required></div>
                    <div><label>Department</label><input type="text" th:field="*{department}" required></div>
                    <div><label>Duty Hours</label><input type="text" th:field="*{dutyHours}" placeholder="e.g., 8am - 5pm" required></div>
                    <div><label>Registration Number</label><input type="text" th:field="*{registrationNumber}" required></div>
                    <div><label>Years of Experience</label><input type="number" th:field="*{yearsOfExperience}" required></div>
                </div>
            </div>

            <!-- Security Info -->
            <div class="card">
                <h3 class="section-title">Security Information</h3>
                <div class="form-grid">
                    <div><label>Security Question</label><input type="text" th:field="*{securityQuestion}" required></div>
                    <div><label>Security Answer</label><input type="text" th:field="*{securityAnswer}" required></div>
                </div>
            </div>

            <!-- Messages -->
            <div th:if="${error}" class="alert alert-danger" th:text="${error}"></div>
            <div th:if="${success}" class="alert alert-success" th:text="${success}"></div>

            <button type="submit" class="register-btn">Register</button>

            <div class="text-center mt-3">
                Already have an account? <a href="/pharmacist/login">Login here</a>
            </div>
        </form>
    </div>

    <!-- RIGHT PANEL -->
    <div class="right-panel">
        <div class="carousel">
            <img src="https://images.unsplash.com/photo-1639772823849-6efbd173043c?auto=format&fit=crop&q=80&w=687" class="active" alt="Pharmacy 1">
            <img src="https://images.unsplash.com/photo-1657551882668-53fdd523c9ff?auto=format&fit=crop&q=60&w=600" alt="Pharmacy 2">
            <img src="https://images.unsplash.com/photo-1659019721449-f2e2b10f49b9?auto=format&fit=crop&q=60&w=600" alt="Pharmacy 3">
        </div>
        <div class="overlay"></div>
        <div class="right-content">
            <h2 class="h2head">Join <span>Our Pharmacist Portal</span></h2>
            <p>Register to manage pharmacy operations, inventory, and patient prescriptions efficiently.</p>
        </div>
    </div>
</div>

<script>
    const images = document.querySelectorAll('.carousel img');
    let current = 0;
    setInterval(() => {
        images[current].classList.remove('active');
        current = (current + 1) % images.length;
        images[current].classList.add('active');
    }, 4000);
</script>

</body>
</html>
