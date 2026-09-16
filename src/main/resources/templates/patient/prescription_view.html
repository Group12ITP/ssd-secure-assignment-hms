<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Prescription Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background-color: #f9fafb;
            font-family: 'Inter', sans-serif;
        }
        .container {
            max-width: 900px;
            margin-top: 40px;
        }
        .card {
            border-radius: 15px;
            border: none;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
            overflow: hidden;
        }
        .card-header {
            background: linear-gradient(90deg, #052c27, #0b5e52);
            color: #fff;
            padding: 25px;
        }
        .card-header h3 {
            font-weight: 700;
        }
        .card-body {
            background-color: #fff;
            padding: 30px;
        }
        .info-section {
            display: flex;
            justify-content: space-between;
            flex-wrap: wrap;
            border-bottom: 1px solid #e0e0e0;
            padding-bottom: 15px;
            margin-bottom: 25px;
        }
        .info-box {
            width: 48%;
            margin-bottom: 10px;
        }
        .info-box h6 {
            color: #888;
            font-weight: 500;
            margin-bottom: 3px;
        }
        .info-box p {
            font-weight: 600;
            margin-bottom: 0;
        }
        table {
            border-radius: 10px;
            overflow: hidden;
        }
        .table th {
            background-color: #f0f4f4;
            color: #333;
        }
        .footer-note {
            margin-top: 30px;
            border-top: 1px dashed #ccc;
            padding-top: 15px;
            color: #666;
        }
        .btn-download {
            background-color: #0b5e52;
            color: #fff;
            border-radius: 8px;
        }
        .btn-download:hover {
            background-color: #09483e;
            color: #fff;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="card">
        <div class="card-header d-flex justify-content-between align-items-center">
            <div>
                <h3>Prescription Details</h3>
                <p class="mb-0">Prescription ID: <span th:text="${prescription.id}">#RX001</span></p>
            </div>
            <button class="btn btn-download"><i class="bi bi-download me-2"></i>Download PDF</button>
        </div>

        <div class="card-body">
            <!-- Info Section -->
            <div class="info-section">
                <div class="info-box">
                    <h6>Patient Name</h6>
                    <p th:text="${patient.fullName}">John Doe</p>
                </div>
                <div class="info-box">
                    <h6>Doctor Name</h6>
                    <p th:text="${doctor.fullName}">Dr. Emma Watson</p>
                </div>
                <div class="info-box">
                    <h6>Department</h6>
                    <p th:text="${doctor.department}">Cardiology</p>
                </div>
                <div class="info-box">
                    <h6>Date Issued</h6>
                    <p th:text="${#dates.format(prescription.date, 'dd-MM-yyyy')}">15-10-2025</p>
                </div>
            </div>

            <!-- Table Section -->
            <h5 class="fw-bold mb-3">Medication List</h5>
            <table class="table align-middle table-hover">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Medicine Name</th>
                    <th>Dosage</th>
                    <th>Frequency</th>
                    <th>Duration</th>
                    <th>Notes</th>
                </tr>
                </thead>
                <tbody>
                <tr th:each="med, iterStat : ${prescription.medicines}">
                    <td th:text="${iterStat.count}"></td>
                    <td th:text="${med.name}"></td>
                    <td th:text="${med.dosage}"></td>
                    <td th:text="${med.frequency}"></td>
                    <td th:text="${med.duration}"></td>
                    <td th:text="${med.notes}"></td>
                </tr>
                <!-- Sample static rows for preview -->

                </tbody>
            </table>

            <!-- Footer Notes -->
            <div class="footer-note">
                <p><strong>Doctor's Advice:</strong> <span th:text="${prescription.advice}">Drink plenty of water and rest adequately.</span></p>
                <p><i class="bi bi-info-circle me-1"></i> If symptoms persist beyond 5 days, please revisit your doctor.</p>
            </div>
        </div>
    </div>
</div>

</body>
</html>
