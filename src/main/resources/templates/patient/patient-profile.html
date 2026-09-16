<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Profile - HealthCare System</title>
    <link rel="stylesheet" th:href="@{/css/styles.css}">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Inter', sans-serif;
            background: #f8fafc;
            color: #1e293b;
        }

        /* Header */
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 1rem 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .header h1 {
            font-size: 1.5rem;
            font-weight: 600;
        }

        .user-info {
            display: flex;
            align-items: center;
            gap: 1rem;
        }

        .user-avatar {
            width: 40px;
            height: 40px;
            background: rgba(255, 255, 255, 0.2);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
        }

        /* Main Container */
        .main-container {
            display: flex;
            min-height: calc(100vh - 80px);
        }

        /* Sidebar */
        .sidebar {
            width: 250px;
            background: white;
            border-right: 1px solid #e2e8f0;
            padding: 2rem 0;
        }

        .nav-item {
            display: flex;
            align-items: center;
            gap: 0.75rem;
            padding: 0.75rem 1.5rem;
            color: #64748b;
            text-decoration: none;
            transition: all 0.2s;
            border-left: 3px solid transparent;
        }

        .nav-item:hover, .nav-item.active {
            background: #f1f5f9;
            color: #3b82f6;
            border-left-color: #3b82f6;
        }

        .nav-item i {
            width: 20px;
            text-align: center;
        }

        /* Content Area */
        .content {
            flex: 1;
            padding: 2rem;
        }

        .content-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2rem;
        }

        .content-header h2 {
            font-size: 1.875rem;
            font-weight: 700;
            color: #1e293b;
        }

        /* Success/Error Messages */
        .alert {
            padding: 0.75rem 1rem;
            border-radius: 0.5rem;
            margin-bottom: 1rem;
        }

        .alert-success {
            background: #d1fae5;
            color: #065f46;
            border: 1px solid #a7f3d0;
        }

        .alert-error {
            background: #fee2e2;
            color: #991b1b;
            border: 1px solid #fca5a5;
        }

        /* Cards */
        .card {
            background: white;
            border-radius: 1rem;
            padding: 1.5rem;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
            border: 1px solid #e2e8f0;
            margin-bottom: 1.5rem;
        }

        .card-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1rem;
            padding-bottom: 1rem;
            border-bottom: 1px solid #e2e8f0;
        }

        .card-title {
            font-size: 1.25rem;
            font-weight: 600;
            color: #1e293b;
        }

        .card-body {
            color: #64748b;
        }

        /* Buttons */
        .btn {
            padding: 0.5rem 1rem;
            border-radius: 0.5rem;
            font-size: 0.875rem;
            font-weight: 500;
            text-decoration: none;
            text-align: center;
            transition: all 0.2s;
            border: none;
            cursor: pointer;
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
        }

        .btn-primary {
            background: #3b82f6;
            color: white;
        }

        .btn-primary:hover {
            background: #2563eb;
        }

        .btn-secondary {
            background: #f1f5f9;
            color: #64748b;
        }

        .btn-secondary:hover {
            background: #e2e8f0;
        }

        .btn-sm {
            padding: 0.25rem 0.75rem;
            font-size: 0.75rem;
        }

        /* Responsive */
        @media (max-width: 768px) {
            .main-container {
                flex-direction: column;
            }
            
            .sidebar {
                width: 100%;
                padding: 1rem 0;
            }
            
            .content-header {
                flex-direction: column;
                gap: 1rem;
                align-items: flex-start;
            }
        }
    </style>
</head>
<body>
    <!-- Header -->
    <header class="header">
        <h1><i class="fas fa-heartbeat"></i> HealthCare System</h1>
        <div class="user-info">
            <span th:text="${patient.fullName}">Patient Name</span>
            <div class="user-avatar" th:text="${#strings.substring(patient.fullName,0,1)} + ${#strings.substring(patient.username,0,1)}">PN</div>
        </div>
    </header>

    <div class="main-container">
        <!-- Sidebar -->
        <nav class="sidebar">
            <a href="/patient/dashboard" class="nav-item">
                <i class="fas fa-home"></i>
                Dashboard
            </a>
            <a href="/patient/appointments" class="nav-item">
                <i class="fas fa-calendar-alt"></i>
                Appointments
            </a>
            <a href="/patient/prescriptions" class="nav-item">
                <i class="fas fa-prescription"></i>
                Prescriptions
            </a>
            <a href="/patient/medications" class="nav-item">
                <i class="fas fa-pills"></i>
                Medications
            </a>
            <a th:href="@{'/patient/profile/' + ${patient.patientId}}" class="nav-item active">
                <i class="fas fa-user"></i>
                Profile
            </a>
            <a href="/patient/logout" class="nav-item">
                <i class="fas fa-sign-out-alt"></i>
                Logout
            </a>
        </nav>

        <!-- Main Content -->
        <main class="content">
            <div class="content-header">
                <h2>My Profile</h2>
                <a href="#" class="btn btn-primary">
                    <i class="fas fa-edit"></i>
                    Edit Profile
                </a>
            </div>

            <!-- Success/Error Messages -->
            <div th:if="${success}" class="alert alert-success" th:text="${success}"></div>
            <div th:if="${error}" class="alert alert-error" th:text="${error}"></div>

            <div class="card">
                <div class="card-header">
                    <h3 class="card-title">Personal Information</h3>
                </div>
                <div class="card-body">
                    <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 1rem;">
                        <div>
                            <strong>Full Name:</strong><br>
                            <span th:text="${patient.fullName}">John Smith</span>
                        </div>
                        <div>
                            <strong>Username:</strong><br>
                            <span th:text="${patient.username}">john.smith</span>
                        </div>
                        <div>
                            <strong>Gender:</strong><br>
                            <span th:text="${patient.gender}">Male</span>
                        </div>
                        <div>
                            <strong>Date of Birth:</strong><br>
                            <span th:text="${patient.dateOfBirth}">15 Jan 1990</span>
                        </div>
                        <div>
                            <strong>Contact Number:</strong><br>
                            <span th:text="${patient.contactNumber}">+1-555-0201</span>
                        </div>
                        <div>
                            <strong>Email:</strong><br>
                            <span th:text="${patient.email}">john.smith@email.com</span>
                        </div>
                        <div>
                            <strong>Blood Group:</strong><br>
                            <span th:text="${patient.bloodGroup}">O+</span>
                        </div>
                        <div>
                            <strong>Emergency Contact:</strong><br>
                            <span th:text="${patient.emergencyContactNumber}">+1-555-0202</span>
                        </div>
                    </div>
                    
                    <div th:if="${patient.address != null}" style="margin-top: 1rem;">
                        <strong>Address:</strong><br>
                        <span th:text="${patient.address}">123 Main St, City, State</span>
                    </div>
                </div>
            </div>

            <div class="card">
                <div class="card-header">
                    <h3 class="card-title">Medical Information</h3>
                </div>
                <div class="card-body">
                    <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 1rem;">
                        <div th:if="${patient.medicalHistory != null}">
                            <strong>Medical History:</strong><br>
                            <span th:text="${patient.medicalHistory}">No significant medical history</span>
                        </div>
                        <div th:if="${patient.allergies != null}">
                            <strong>Allergies:</strong><br>
                            <span th:text="${patient.allergies}">None known</span>
                        </div>
                        <div th:if="${patient.currentMedications != null}">
                            <strong>Current Medications:</strong><br>
                            <span th:text="${patient.currentMedications}">None</span>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card">
                <div class="card-header">
                    <h3 class="card-title">Account Information</h3>
                </div>
                <div class="card-body">
                    <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 1rem;">
                        <div>
                            <strong>Patient ID:</strong><br>
                            <span th:text="${patient.patientId}">P001</span>
                        </div>
                        <div>
                            <strong>NIC:</strong><br>
                            <span th:text="${patient.nicPassport}">9876543210</span>
                        </div>
                        <div>
                            <strong>Security Question:</strong><br>
                            <span th:text="${patient.securityQuestion}">What is your mother's maiden name?</span>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</body>
</html>