# SSD – Secure Assignment (Hospital Management System)

## Group Members
- [Member 1 Name] – [Index Number]
- [Member 2 Name] – [Index Number]
- [Member 3 Name] – [Index Number]
- [Member 4 Name] – [Index Number]

## Original Project
- Original Repository: `<link-to-original-repo>`
- Baseline Reference: Commit `cb653ae0c986ed5230f6ef65257978c14c25dd7b` (Imported from original repository; last commit before semester start used as the pre-fix baseline).

## Modified Project
- Assignment Repository: `<link-to-this-new-repo>`

## Assignment Overview
This project is an enterprise Hospital Management System (HMS) developed with Spring Boot, Thymeleaf, and Spring Security. As part of the SE4030 Secure Software Development module, a comprehensive white-box source code security audit and threat analysis were conducted against the pre-fix baseline. Seventeen distinct security vulnerabilities across the OWASP Top 10 (2021), OWASP API Top 10, and CWE catalogs were cataloged. A series of isolated, targeted remediation branches are executed to fix each vulnerability progressively, followed by the addition of OAuth2 / OpenID Connect single sign-on authentication.

---

## Vulnerability Summary Table

| ID | Title | OWASP Category | Severity | Status |
|---|---|---|---|---|
| **V1** | CSRF Protection Disabled Globally | A01:2021 – Broken Access Control / A05:2021 – Security Misconfiguration | High | **Fixed** |
| **V2** | Publicly Accessible Protected Routes (`anyRequest().permitAll()`) | A01:2021 – Broken Access Control | Critical | **Fixed** |
| **V3** | Authentication Bypass via URL Parameter in Doctor Portal | A07:2021 – Identification & Authentication Failures | Critical | **Fixed** |
| **V4** | Authentication Bypass via URL Parameter in Pharmacist Portal | A07:2021 – Identification & Authentication Failures | Critical | **Fixed** |
| **V5** | Insecure Direct Object Reference (IDOR) on Patient Profile | A01:2021 – Broken Access Control | High | **Fixed** |
| **V6** | Broken Object Level Authorization (BOLA/IDOR) on Prescriptions | A01:2021 – Broken Access Control | High | **Fixed** |
| **V7** | DOM-Based Cross-Site Scripting (DOM XSS) in Landing Page Testimonials | A03:2021 – Injection | High | **Fixed** |
| **V8** | Stored DOM XSS in Prescription Medicine List Rendering | A03:2021 – Injection | High | **Fixed** |
| **V9** | Hardcoded Credentials & Insecure Database Configuration | A05:2021 – Security Misconfiguration / A02:2021 – Cryptographic Failures | High | **Fixed** |
| **V10** | Session Fixation Vulnerability in Authentication Handlers | A07:2021 – Identification & Authentication Failures | Medium | **Fixed** |
| **V11** | Information Disclosure & State Mutation via Debug Endpoints | A05:2021 – Security Misconfiguration | Medium | **Fixed** |
| **V12** | Missing Authorization & Input Validation on Medicine Creation | A01:2021 – Broken Access Control / A04:2021 – Insecure Design | Medium | **Fixed** |
| **V13** | CSV / Formula Injection in Daily Reports Export | A03:2021 – Injection | Medium | **Fixed** |
| **V14** | CRLF / SMTP Header Injection in Contact Form | A03:2021 – Injection | Medium | Planned (Next) |
| **V15** | Missing Security HTTP Response Headers (CSP, Frame Options) | A05:2021 – Security Misconfiguration | Medium | Planned |
| **V16** | Protected Health Information (PHI) Leaked to Standard Output | A09:2021 – Security Logging and Monitoring Failures | Low | Planned |
| **V17** | Weak Password Policy & Missing Complexity Validation | A07:2021 – Identification & Authentication Failures | Low | Planned |

---

## Detailed Findings

### V1: CSRF Protection Disabled Globally
- **OWASP Category:** A01:2021 – Broken Access Control / A05:2021 – Security Misconfiguration (CWE-352: Cross-Site Request Forgery)
- **Severity:** High
- **Affected Files:**
  - `src/main/java/com/example/test/Security/WebSecurityConfig.java`
  - `src/main/resources/templates/pharmacist/pharmacist-dashboard.html`
- **Description:**
  CSRF protection was explicitly disabled globally in `WebSecurityConfig` with `.csrf().disable()`. Consequently, state-changing HTTP `POST` endpoints (prescription creation, appointment updates, medicine additions, status modifications) lacked anti-forgery validation.
- **How It Was Identified:**
  Manual static code review and inspection of Spring Security filter configurations.
- **Exploitation Scenario:**
  An authenticated doctor or pharmacist visits a malicious website hosting an invisible, auto-submitting HTML form targeting `http://localhost:8081/prescription/create` or `/prescription/update-status/1?status=Processing`. Because cookies are automatically included in cross-origin browser requests, the unauthorized action executes with the victim's privileges.
- **Fix Applied:**
  1. Replaced `.csrf().disable()` with `CookieCsrfTokenRepository.withHttpOnlyFalse()` in `WebSecurityConfig.java`.
  2. Verified Thymeleaf forms automatically generate hidden `_csrf` input fields for all `th:action` endpoints.
  3. Added `_csrf` meta tags and updated client-side JavaScript in `pharmacist-dashboard.html` to pass the `X-XSRF-TOKEN` header on asynchronous POST requests.
- **Branch:** `fix/V1-csrf-protection`
- **Commit:** `ffd3054`
- **Verification:**
  - Built successfully via Maven (`BUILD SUCCESS`).
  - Tested that submitting POST requests without a valid CSRF token results in HTTP 403 Forbidden.
  - Verified that legitimate Thymeleaf form submissions and token-authenticated AJAX calls succeed.
- **Preventive Best Practice:**
  Never disable CSRF protection for browser-facing web applications. Utilize standard token repositories (synchronizer token pattern or double-submit cookies) and enforce SameSite cookie attributes.

---

### V2: Publicly Accessible Protected Routes (`anyRequest().permitAll()`)
- **OWASP Category:** A01:2021 – Broken Access Control (CWE-284: Improper Access Control)
- **Severity:** Critical
- **Affected Files:**
  - `src/main/java/com/example/test/Security/WebSecurityConfig.java`
  - `src/main/java/com/example/test/Controller/DoctorController.java`
  - `src/main/java/com/example/test/Controller/AuthController.java`
  - `src/main/java/com/example/test/Controller/PatientController.java`
- **Description:**
  In the baseline `WebSecurityConfig`, the HTTP request authorization chain concluded with `.anyRequest().permitAll()`, despite code comments indicating everything else should require authentication. As a result, all backend routes—including clinical doctor dashboards, patient medical portals, pharmacist queues, and prescription administration—were entirely unauthenticated at the perimeter level.
- **How It Was Identified:**
  Manual static code review of `WebSecurityConfig.java` and white-box access control flow mapping.
- **Exploitation Scenario:**
  An unauthenticated remote attacker directly navigates to `/doctor/dashboard`, `/pharmacist/dashboard`, or `/patient/dashboard`. Because Spring Security permits all requests, any request bypassing application-level session checks directly exposed internal functionality and medical management interfaces without user credentials.
- **Fix Applied:**
  1. Created `SecurityRoles.java` defining standard role constants (`ROLE_DOCTOR`, `ROLE_PHARMACIST`, `ROLE_PATIENT`).
  2. Replaced `.anyRequest().permitAll()` in `WebSecurityConfig.java` with ordered authorization rules:
     - Static assets (`/css/**`, `/js/**`, `/images/**`, `/webjars/**`, `/favicon.ico`) &rarr; `permitAll()`
     - Public landing, error, and info routes (`/`, `/home`, `/logins`, `/registers`, `/contact/**`, `/error`) &rarr; `permitAll()`
     - Public authentication endpoints (`/doctor/login`, `/doctor/register`, `/pharmacist/login`, `/pharmacist/register`, `/patient/login`, `/patient/register`, `/patient/logout`) &rarr; `permitAll()`
     - Role-specific portals: `/doctor/**` &rarr; `hasRole("DOCTOR")`, `/pharmacist/**` &rarr; `hasRole("PHARMACIST")`, `/patient/**` &rarr; `hasRole("PATIENT")`
     - Global fallback: `.anyRequest().authenticated()`
  3. Added an `AuthenticationEntryPoint` and `AccessDeniedHandler` redirecting unauthenticated and unauthorized requests to `/logins` (and `/logins?denied=true`).
  4. Updated login controllers (`DoctorController`, `AuthController`, `PatientController`) to bind the authenticated principal and granted authority to the `SecurityContext` upon successful password verification.
- **Branch:** `fix/V2-enforce-url-authorization`
- **Commit:** `5a945fa`
- **Verification:**
  - Verified project compilation via Maven with JDK 17 (`BUILD SUCCESS`).
  - Verified that unauthenticated requests to `/doctor/dashboard`, `/pharmacist/dashboard`, and `/patient/dashboard` are intercepted by Spring Security and redirected to `/logins`.
  - Verified public static assets and landing pages remain accessible without authentication.
- **Preventive Best Practice:**
  Adopt a "Deny by Default" access control paradigm in Spring Security. Ensure all endpoints are authenticated by default, explicitly whitelisting only strictly public resources. Implement automated integration tests asserting HTTP 302/401/403 responses on protected endpoints for unauthenticated clients.

---

### V3: Authentication Bypass via URL Parameter in Doctor Portal
- **OWASP Category:** A07:2021 – Identification & Authentication Failures / A01:2021 – Broken Access Control (CWE-287: Improper Authentication, CWE-306: Missing Authentication for Critical Function)
- **Severity:** Critical
- **Affected Files:**
  - `src/main/java/com/example/test/Controller/DoctorController.java`
- **Description:**
  `DoctorController` inspected `@RequestParam(required = false) String username` in `showDashboard`, `listAppointments`, `calendar`, and `profile`. If no active session was present, the application invoked `doctorService.getDoctorByUsername(username)` and automatically established an active doctor session (`session.setAttribute("doctor", doctor)`), completely bypassing password verification.
- **How It Was Identified:**
  Manual static code review and control flow analysis of `DoctorController.java`.
- **Exploitation Scenario:**
  An attacker navigates to `/doctor/dashboard?username=sarah.johnson`. The application loads Dr. Sarah Johnson's entity, stores it into the HTTP session without authenticating credentials, and displays confidential appointments, schedules, and clinical dashboard metrics.
- **Fix Applied:**
  Removed all `username` request parameter parsing and parameter-based session population across `showDashboard`, `listAppointments`, `calendar`, and `profile` in `DoctorController.java`. User identity is strictly resolved from the authenticated session context, redirecting unauthenticated requests to `/doctor/login`.
- **Branch:** `fix/V3-prevent-doctor-auth-bypass`
- **Commit:** `a1c0398`
- **Verification:**
  - Verified compilation with Maven wrapper and JDK 17 (`BUILD SUCCESS`).
  - Verified that requesting `/doctor/dashboard?username=sarah.johnson` without an authenticated session fails to grant access and redirects to `/doctor/login`.
- **Preventive Best Practice:**
  Never use client-supplied query parameters or request headers as proof of identity. Authenticate credentials once via a trusted authentication provider and maintain identity strictly in cryptographically secure, server-side session contexts or signed tokens.

---

### V4: Authentication Bypass via URL Parameter in Pharmacist Portal
- **OWASP Category:** A07:2021 – Identification & Authentication Failures / A01:2021 – Broken Access Control (CWE-287: Improper Authentication, CWE-306: Missing Authentication for Critical Function)
- **Severity:** Critical
- **Affected Files:**
  - `src/main/java/com/example/test/Controller/PharmacistController.java`
- **Description:**
  `PharmacistController.showDashboard` accepted `@RequestParam(required = false) String username`. If present, it bypassed session verification and fetched the pharmacist by the supplied username string, exposing confidential prescription queues, urgency counters, and scheduled fulfillment lists without authentication. In addition, `/pharmacist/patients` had no session validation check.
- **How It Was Identified:**
  Manual static code review and endpoint parameter mapping in `PharmacistController.java`.
- **Exploitation Scenario:**
  An unauthenticated remote user visits `/pharmacist/dashboard?username=alice`. The application loads pharmacist Alice's dashboard, displaying pending orders, urgent prescriptions, and inventory shortcuts without requesting a password.
- **Fix Applied:**
  1. Removed `@RequestParam String username` from `showDashboard` in `PharmacistController.java`.
  2. Enforced that the pharmacist username must strictly originate from an active, verified `HttpSession` attribute (`pharmacistUsername`), redirecting unauthenticated requests to `/pharmacist/login`.
  3. Added session validation to `/pharmacist/patients`.
- **Branch:** `fix/V4-prevent-pharmacist-auth-bypass`
- **Commit:** `8725b2d`
- **Verification:**
  - Compiled successfully with Maven wrapper (`BUILD SUCCESS`).
  - Tested that navigating to `/pharmacist/dashboard?username=alice` without an active pharmacist session redirects to `/pharmacist/login`.
- **Preventive Best Practice:**
  Enforce strict session-bound authentication principles. Reject query parameters for identity management and mandate multi-layered access control where both framework security filters and controllers enforce authenticated principal state.

---

### V5: Insecure Direct Object Reference (IDOR) on Patient Profile
- **OWASP Category:** A01:2021 – Broken Access Control (CWE-639: Authorization Bypass Through User-Controlled Key)
- **Severity:** High
- **Affected Files:**
  - `src/main/java/com/example/test/Controller/PatientController.java`
- **Description:**
  The `showPatientProfile` endpoint (`/patient/profile/{id}`) accepted an arbitrary patient ID path variable and retrieved medical/personal records without verifying caller identity or ensuring the authenticated patient owns the record.
- **How It Was Identified:**
  Manual code review of endpoint authorization checks in `PatientController.java`.
- **Exploitation Scenario:**
  A logged-in patient or unauthenticated attacker navigates to `/patient/profile/1`, `/patient/profile/2`, exposing other patients' sensitive information including full names, NIC/Passport numbers, home addresses, phone numbers, allergies, chronic medical conditions, medications, and emergency contacts.
- **Fix Applied:**
  Updated `showPatientProfile` to validate the active session and compare the requested path `{id}` against the logged-in patient's verified `patientId`. Any mismatch results in access denial and a redirect back to `/patient/dashboard`.
- **Branch:** `fix/V5-prevent-patient-profile-idor`
- **Commit:** `d2aa3a7`
- **Verification:**
  - Compiled successfully with Maven wrapper (`BUILD SUCCESS`).
  - Verified that accessing `/patient/profile/{id}` of a different patient ID redirects to dashboard with an access denied message.
- **Preventive Best Practice:**
  Always validate that the authenticated identity has explicit authorization to access the specific object reference being requested. Avoid relying on client-provided IDs for authorization decisions.

---

### V6: Broken Object Level Authorization (BOLA/IDOR) on Prescriptions
- **OWASP Category:** A01:2021 – Broken Access Control (OWASP API1:2023 – Broken Object Level Authorization, CWE-639)
- **Severity:** High
- **Affected Files:**
  - `src/main/java/com/example/test/Controller/PrescriptionController.java`
- **Description:**
  Prescription creation (`/prescription/create/{appointmentId}` and `POST /prescription/create`), patient prescription history (`/prescription/patient/{patientId}`), full prescription details (`/prescription/details/{prescriptionId}`), and prescription fulfillment status updates (`POST /prescription/update-status/{prescriptionId}`) lacked caller authorization and relationship checks. Any requester could view or mutate arbitrary prescription records across the hospital.
- **How It Was Identified:**
  Manual static code review and endpoint authorization flow mapping in `PrescriptionController.java`.
- **Exploitation Scenario:**
  An unauthorized user sends `POST /prescription/update-status/1?status=Cancelled` to alter medical treatment states, or navigates to `/prescription/details/1` to view private medication regiments, dosages, and clinical diagnoses of patients treated by other clinicians.
- **Fix Applied:**
  1. Enforced doctor session verification and appointment ownership verification in `showCreatePrescriptionForm` and `createPrescription`, binding `prescription.doctorId` strictly to the logged-in doctor.
  2. Restricted `/prescription/patient/{patientId}` so patients may only access their own prescriptions, while doctors and pharmacists retain authorized clinical access.
  3. Added object-level ownership checks on `/prescription/details/{prescriptionId}` ensuring patients only view their own prescriptions and doctors only view prescriptions they authored.
  4. Restricted `/prescription/update-status/{prescriptionId}` and `/pharmacist/orders` exclusively to verified pharmacist sessions.
- **Branch:** `fix/V6-prevent-prescription-bola-idor`
- **Commit:** `01bf952`
- **Verification:**
  - Compiled successfully with Maven wrapper (`BUILD SUCCESS`).
  - Verified that unauthorized access attempts across patient, doctor, and status update endpoints are rejected and redirected appropriately.
- **Preventive Best Practice:**
  Implement contextual authorization checks at the service and controller layers. Validate that the requesting user possesses the requisite role and a legitimate relationship (author, patient owner, or assigned pharmacist) to the specific database record being accessed.

---

### V12: Missing Authorization & Input Validation on Medicine Creation
- **OWASP Category:** A01:2021 – Broken Access Control / A04:2021 – Insecure Design (CWE-285, CWE-20)
- **Severity:** Medium
- **Affected Files:**
  - `src/main/java/com/example/test/Controller/MedicineController.java`
  - `src/main/java/com/example/test/Model/Medicine.java`
  - `src/main/java/com/example/test/Security/WebSecurityConfig.java`
- **Description:**
  Medicine creation and inventory management routes (`/medicine/**`, `/medicine/add`, `/medicine/api/**`) lacked role-based access restrictions, allowing any unauthenticated or unauthorized party to create pharmaceutical entries. Furthermore, the `POST /medicine/add` endpoint accepted incoming data without executing Bean Validation (`@Valid` was missing on `@ModelAttribute Medicine medicine`), and numerical fields like `unitPrice` and `stockQuantity` lacked non-negativity constraints.
- **How It Was Identified:**
  Source code auditing of `MedicineController.java` and entity constraints in `Medicine.java`.
- **Exploitation Scenario:**
  An unauthorized actor or malicious patient sends `POST /medicine/add` with arbitrary or negative values for `unitPrice` and `stockQuantity`, or injects counterfeit medication items into the hospital catalog without pharmacist credentials.
- **Fix Applied:**
  1. Configured HTTP-level access control in `WebSecurityConfig.java` requiring `ROLE_PHARMACIST` for `/medicine/**`.
  2. Implemented controller-level pharmacist session checks across `listMedicines`, `addMedicineForm`, `saveMedicine`, and API endpoints in `MedicineController.java`.
  3. Added `@Valid` and `BindingResult` to `saveMedicine` to enforce bean validation before saving, returning form errors if validation fails.
  4. Added `@PositiveOrZero` and `@Min(0)` constraints on `unitPrice` and `stockQuantity` in `Medicine.java`.
- **Branch:** `fix/V12-restrict-medicine-creation`
- **Commit:** `e39fded`
- **Verification:**
  - Verified compilation via Maven wrapper (`BUILD SUCCESS`).
  - Confirmed that non-pharmacist requests to `/medicine/add` are redirected to `/pharmacist/login`.
  - Confirmed that invalid input is rejected before reaching the database.
- **Preventive Best Practice:**
  Apply strict input validation on all mutable inputs using Jakarta Validation annotations, and pair endpoint security in `SecurityFilterChain` with application-layer authorization checks to prevent unauthorized state creation.

---

### V9: Hardcoded Credentials & Insecure Database Configuration
- **OWASP Category:** A05:2021 – Security Misconfiguration / A02:2021 – Cryptographic Failures (CWE-798, CWE-295)
- **Severity:** High
- **Affected Files:**
  - `src/main/resources/application.properties`
  - `src/main/resources/application.properties.example`
- **Description:**
  Database connection credentials (`spring.datasource.username=app_user` and `spring.datasource.password=123`) were hardcoded in plain text directly in the repository's configuration file. Furthermore, the JDBC connection URL specified `trustServerCertificate=true`, disabling TLS certificate verification and exposing database traffic (including patient PHI, prescription histories, and password hashes) to Machine-in-the-Middle (MitM) inspection. Lastly, `spring.jpa.show-sql=true` enabled verbose query and data logging in application output.
- **How It Was Identified:**
  Configuration review of `src/main/resources/application.properties` and static analysis credential scanning.
- **Exploitation Scenario:**
  Anyone with access to the source code repository or build artifacts obtains the cleartext database password `123`. Furthermore, on the local hospital or cloud network, an adversary intercepting database traffic can impersonate the SQL Server database because certificate validation is explicitly disabled (`trustServerCertificate=true`).
- **Fix Applied:**
  1. Externalized `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` into environment variables (`SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`) with no hardcoded secrets in the codebase.
  2. Changed default TLS behavior to `trustServerCertificate=false` to mandate certificate trust validation.
  3. Changed `spring.jpa.hibernate.ddl-auto` default to `validate` and `spring.jpa.show-sql` to `false` via configurable property placeholders.
  4. Provided `application.properties.example` as a safe configuration template for production deployments.
- **Branch:** `fix/V9-externalize-credentials-secure-tls`
- **Commit:** `8b53b67`
- **Verification:**
  - Verified compilation via Maven wrapper (`BUILD SUCCESS`).
  - Confirmed application loads credentials securely from environment variables without exposing cleartext credentials in source control.
- **Preventive Best Practice:**
  Never commit database credentials or API secrets to version control. Utilize environment variables, secrets managers (e.g., HashiCorp Vault, AWS Secrets Manager, Azure Key Vault), and enforce strict TLS server certificate verification on all database and microservice connections.

---

### V10: Session Fixation Vulnerability in Authentication Handlers
- **OWASP Category:** A07:2021 – Identification & Authentication Failures (CWE-384)
- **Severity:** Medium
- **Affected Files:**
  - `src/main/java/com/example/test/Controller/AuthController.java`
  - `src/main/java/com/example/test/Controller/DoctorController.java`
  - `src/main/java/com/example/test/Controller/PatientController.java`
- **Description:**
  Authentication handlers across the doctor, pharmacist, and patient portals (`/doctor/login`, `/pharmacist/login`, and `/patient/login`) authenticated users and assigned session attributes without renewing or rotating the underlying HTTP session identifier. An attacker could pre-seed a target browser with a known session token (`JSESSIONID`) and wait for the victim to log in, enabling full account hijacking.
- **How It Was Identified:**
  Source code review of session establishment flows in all three login controllers.
- **Exploitation Scenario:**
  An adversary on a shared workstation or via subdomain cookie tossing plants a specific `JSESSIONID` in the victim's browser. When the victim (doctor or pharmacist) logs in, the server elevates privileges on the pre-existing session without changing the identifier. The attacker then uses the pre-seeded session token to access protected clinical records.
- **Fix Applied:**
  Invoked `request.changeSessionId()` immediately upon successful credential validation across `AuthController.java`, `DoctorController.java`, and `PatientController.java`. This forces the servlet container to issue a brand new `JSESSIONID` cookie and migrate session attributes while invalidating the pre-authentication session identifier.
- **Branch:** `fix/V10-prevent-session-fixation`
- **Commit:** `c4ececc`
- **Verification:**
  - Verified compilation via Maven wrapper (`BUILD SUCCESS`).
  - Confirmed session ID rotation upon successful credential verification.
- **Preventive Best Practice:**
  Always invalidate the existing session or invoke session ID migration (`request.changeSessionId()`) immediately upon privilege elevation or successful user authentication to defend against session fixation attacks.

---

### V7: DOM-Based Cross-Site Scripting (DOM XSS) in Landing Page Testimonials
- **OWASP Category:** A03:2021 – Injection (CWE-79)
- **Severity:** High
- **Affected Files:**
  - `src/main/resources/templates/index.html`
- **Description:**
  The landing page client-side feedback submission handler took user-provided input values (`name` and `dept` from `#fbName` and `#fbDept`) and directly concatenated them into an HTML template string assigned to `slide.innerHTML`. Any JavaScript payload included in the name or department fields would be parsed as executable markup and executed immediately in the user's browser context.
- **How It Was Identified:**
  Front-end code review of DOM manipulation routines in `src/main/resources/templates/index.html:664-678`.
- **Exploitation Scenario:**
  An attacker inputs `<img src=x onerror=alert(document.cookie)>` or a script payload into the feedback name or department field. Upon submitting the form, the browser parses the HTML fragment and executes the injected script, which can steal session tokens or perform malicious actions on behalf of the user.
- **Fix Applied:**
  Eliminated dynamic string interpolation into `slide.innerHTML`. Constructed the DOM skeleton with static markup classes (`.name-holder`, `.dept-holder`, `.quote-holder`, `.avatar-img`) and populated all dynamic user inputs strictly via `textContent` and safely URL-encoded attributes (`encodeURIComponent(name)`).
- **Branch:** `fix/V7-prevent-dom-xss-testimonials`
- **Commit:** `79ded36`
- **Verification:**
  - Verified compilation and template integrity via Maven wrapper (`BUILD SUCCESS`).
  - Tested HTML markup strings (e.g. `<img src=x onerror=alert(1)>`) and verified they render safely as literal text without DOM script execution.
- **Preventive Best Practice:**
  Avoid using `.innerHTML`, `outerHTML`, or `document.write` with untrusted data. Use browser-native safe manipulation methods such as `.textContent`, `document.createElement`, and `.setAttribute`, or employ a trusted sanitization library like DOMPurify.

---

### V8: Stored DOM XSS in Prescription Medicine List Rendering
- **OWASP Category:** A03:2021 – Injection (CWE-79)
- **Severity:** High
- **Affected Files:**
  - `src/main/resources/templates/doctor/create-prescription.html`
  - `src/main/resources/templates/pharmacist/pharmacist-dashboard.html`
  - `src/main/resources/templates/pharmacist/scheduled-orders.html`
- **Description:**
  Client-side scripts across the doctor prescription creation portal and pharmacist dashboard rendered asynchronous medication payloads (`/prescription/medicines/{category}` and `/prescription/api/medicines/{orderId}`) by directly interpolating medication properties (`medicineName`, `genericName`, `strength`, `dosage`, `frequency`, `instructions`) into template literals assigned to `.innerHTML`. Furthermore, dynamic inline `onclick` handlers in `create-prescription.html` performed raw string concatenation of medication parameters. Any malicious payload persisted in medication or prescription records would execute in clinicians' and pharmacists' browsers upon viewing.
- **How It Was Identified:**
  Source code review of dynamic JavaScript rendering blocks across Thymeleaf portal templates.
- **Exploitation Scenario:**
  An adversary creates or updates a medicine record with a name such as `Amoxicillin<script>fetch('/stealer?c='+document.cookie)</script>` or injects XSS via prescription dosage/instructions. Whenever a doctor selects that medicine category or a pharmacist views scheduled orders, the browser renders the unescaped HTML string, executing the payload in the context of the authenticated healthcare worker.
- **Fix Applied:**
  1. Replaced all `.innerHTML` string interpolations across `create-prescription.html`, `pharmacist-dashboard.html`, and `scheduled-orders.html` with explicit DOM element creation (`document.createElement`) and safe text assignment (`textContent`).
  2. Replaced unsafe inline `onclick="..."` string concatenations in `create-prescription.html` with programmatic `addEventListener('click', ...)` closures passing typed parameters.
- **Branch:** `fix/V8-prevent-medicine-dom-xss`
- **Commit:** `1e4687f`
- **Verification:**
  - Verified compilation via Maven wrapper (`BUILD SUCCESS`).
  - Confirmed that medicines containing HTML or script tags render as escaped literal strings and do not trigger browser script execution.
- **Preventive Best Practice:**
  Adopt strict DOM-safe rendering guidelines. Avoid template literal string concatenation into `innerHTML`. Use `textContent` for untrusted textual data, and attach event listeners programmatically rather than building inline handler attributes.

---

### V11: Information Disclosure & State Mutation via Debug Endpoints
- **OWASP Category:** A05:2021 – Security Misconfiguration (CWE-489, CWE-209)
- **Severity:** Medium
- **Affected Files:**
  - `src/main/java/com/example/test/Controller/TestController.java`
  - `src/main/java/com/example/test/Controller/PrescriptionController.java`
  - `src/main/java/com/example/test/Exception/GlobalExceptionHandler.java`
- **Description:**
  1. `TestController.java` exposed publicly accessible routes (`/test-db`, `/test-prescription-save`, `/create-sample-appointments`, `/test-page`, `/test`) that allowed unauthenticated users to trigger arbitrary database state insertions and view database exception messages.
  2. `PrescriptionController.java` retained an exposed debug route (`/debug`) displaying clinical prescription counts.
  3. `GlobalExceptionHandler.java` handled unhandled server exceptions by reflecting raw `ex.getMessage()` strings to client HTTP responses, disclosing internal implementation details, table structures, and stack traces.
- **How It Was Identified:**
  Endpoint discovery, URL mapping analysis, and exception handler code auditing.
- **Exploitation Scenario:**
  An unauthenticated attacker repeatedly triggers `GET /test-prescription-save` or `/create-sample-appointments` to pollute medical appointment databases with junk records, or triggers database exceptions to gather SQL database version and schema details from the rendered error page.
- **Fix Applied:**
  1. Annotated `TestController` with `@Profile("dev")` to ensure it is completely deactivated in production environments, and sanitized its internal debug outputs and error logging.
  2. Deleted the leftover `/debug` route from `PrescriptionController.java`.
  3. Updated `GlobalExceptionHandler.java` to log full stack traces securely via SLF4J, return a sanitized generic error message to end users, and re-throw `AccessDeniedException` so Spring Security can handle authentication redirects seamlessly.
- **Branch:** `fix/V11-remove-debug-endpoints-sanitize-errors`
- **Commit:** `e7617b6`
- **Verification:**
  - Verified compilation via Maven wrapper (`BUILD SUCCESS`).
  - Confirmed exception messages display sanitized generic text while recording detailed diagnostics to server logs.
- **Preventive Best Practice:**
  Strip all diagnostic, test, and debug endpoints prior to production deployment or isolate them strictly using Spring profiles (`@Profile("dev")`). Ensure global exception handlers never leak raw stack traces or internal database diagnostics to clients.

---

### V13: CSV / Formula Injection in Daily Reports Export
- **OWASP Category:** A03:2021 – Injection (CWE-1236)
- **Severity:** Medium
- **Affected Files:**
  - `src/main/java/com/example/test/Controller/PrescriptionController.java`
- **Description:**
  The prescription daily report export endpoint (`/reports/today.csv`) constructed CSV files by concatenating unvalidated model fields (`status`, `diagnosis`) directly into comma-separated lines. If user-controlled fields contained formula trigger characters (`=`, `+`, `-`, `@`, `\t`, `\r`), spreadsheet viewers (such as Microsoft Excel and LibreOffice Calc) would execute the values as active formulas upon opening the exported CSV file. Additionally, the export lacked session authentication checks.
- **How It Was Identified:**
  Source code auditing of CSV report generator in `PrescriptionController.java`.
- **Exploitation Scenario:**
  A malicious clinician or patient inputs a diagnosis such as `=cmd|'/C powershell IEX ...'!A0` or `=SUM(...)`. When hospital administration or pharmacy staff exports and opens `report-today.csv` in Excel, the operating system executes the spreadsheet macro/formula, leading to remote code execution or data exfiltration.
- **Fix Applied:**
  1. Implemented `sanitizeCsvField()` to prepend a single quote (`'`) to any field starting with formula execution characters (`=`, `+`, `-`, `@`, `\t`, `\r`), forcing spreadsheet software to treat the value strictly as literal text.
  2. Applied RFC 4180 quotation and double-quote escaping for all text fields.
  3. Added session-based authorization checks to ensure only authenticated healthcare staff can download hospital reports.
- **Branch:** `fix/V13-prevent-csv-injection`
- **Commit:** `7a61f77`
- **Verification:**
  - Verified compilation via Maven wrapper (`BUILD SUCCESS`).
  - Tested CSV generation with formula strings (e.g. `=1+1`, `@SUM`) and confirmed they are safely prefixed with `'` in the generated CSV output.
- **Preventive Best Practice:**
  Always sanitize CSV export fields containing free-form user input by neutralizing formula prefix characters (`=`, `+`, `-`, `@`, tab, return) with a prepended single quotation mark (`'`), and wrap string columns in quotes with escaped inner double quotes according to RFC 4180.

---

### Unfixed Vulnerabilities
*(Will be populated for any vulnerabilities remaining intentionally unfixed after remediation phases)*

---

## OAuth2 / OpenID Connect Implementation
*(To be implemented on branch `feature/oauth-openid` following completion of vulnerability fixes)*
- **Provider Used:** Google Identity Services (OpenID Connect)
- **Grant Type:** Authorization Code Grant with PKCE
- **Files Changed:** TBD
- **Branch:** `feature/oauth-openid`
- **Integration Summary:** TBD
- **Testing & Verification:** TBD

---

## Tools Used
- **White-box Review:** Manual source code auditing, Git diff inspection, Maven dependency verification.
- **Static Analysis (SAST):** Semgrep, IDE security linters.
- **Software Composition Analysis (SCA):** Maven Dependency-Check.
- **Dynamic Analysis (DAST):** OWASP ZAP (Zed Attack Proxy) for endpoint interception and parameter testing.

---

## Best Practices to Prevent These Vulnerabilities
1. **Secure SDLC:** Integrate security checkpoints (threat modeling, architectural reviews, automated scans) at each stage of development.
2. **Principle of Least Privilege & Default Deny:** Apply least privilege across web access control, database connections, and session management.
3. **Automated Security Gates in CI/CD:** Mandate SonarQube, Semgrep, and OWASP Dependency-Check passes prior to pull request merges.
4. **Input Validation and Safe Output Encoding:** Enforce strong Bean Validation (`@Valid`, regex complexity rules) and use contextual encoding/safe DOM manipulation to eliminate injection vectors.
5. **Continuous Developer Security Training:** Train developers on OWASP Top 10 risks and secure framework idioms.

---

## Commit and Branch Convention
- One branch per vulnerability: `fix/V<n>-<short-name>`
- Feature branch for OAuth: `feature/oauth-openid`
- Commit message format:
  - Code Fix: `Fix(V<n>): <short description>`
  - Feature: `Feat: Add Google OAuth2/OpenID Connect login`
  - Evidence Log Documentation: `Docs: Add V<n> details to README`
