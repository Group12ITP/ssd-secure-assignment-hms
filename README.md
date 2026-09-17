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
| **V5** | Insecure Direct Object Reference (IDOR) on Patient Profile | A01:2021 – Broken Access Control | High | Planned (Next) |
| **V6** | Broken Object Level Authorization (BOLA/IDOR) on Prescriptions | A01:2021 – Broken Access Control | High | Planned |
| **V7** | DOM-Based Cross-Site Scripting (DOM XSS) in Landing Page Testimonials | A03:2021 – Injection | High | Planned |
| **V8** | Stored DOM XSS in Prescription Medicine List Rendering | A03:2021 – Injection | High | Planned |
| **V9** | Hardcoded Credentials & Insecure Database Configuration | A05:2021 – Security Misconfiguration / A02:2021 – Cryptographic Failures | High | Planned |
| **V10** | Session Fixation Vulnerability in Authentication Handlers | A07:2021 – Identification & Authentication Failures | Medium | Planned |
| **V11** | Information Disclosure & State Mutation via Debug Endpoints | A05:2021 – Security Misconfiguration | Medium | Planned |
| **V12** | Missing Authorization & Input Validation on Medicine Creation | A01:2021 – Broken Access Control / A04:2021 – Insecure Design | Medium | Planned |
| **V13** | CSV / Formula Injection in Daily Reports Export | A03:2021 – Injection | Medium | Planned |
| **V14** | CRLF / SMTP Header Injection in Contact Form | A03:2021 – Injection | Medium | Planned |
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
