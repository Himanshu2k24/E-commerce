# API Documentation

## User Management API

### Register User
**Endpoint:** `/UserServlet`
**Method:** POST

#### Parameters
| Name     | Type   | Required | Description                    |
|----------|--------|----------|--------------------------------|
| action   | String | Yes      | Must be "register"            |
| username | String | Yes      | 3-20 alphanumeric characters  |
| email    | String | Yes      | Valid email format            |
| password | String | Yes      | Minimum 6 characters          |
| name     | String | No       | User's full name              |
| address  | String | No       | User's address                |

#### Response
- Success: Redirects to login.jsp with registered=true
- Error: Redirects to register.jsp with error parameter

### Login
**Endpoint:** `/UserServlet`
**Method:** POST

#### Parameters
| Name     | Type   | Required | Description         |
|----------|--------|----------|---------------------|
| action   | String | Yes      | Must be "login"     |
| email    | String | Yes      | Registered email    |
| password | String | Yes      | Account password    |

#### Response
- Success: Redirects to index.jsp with session created
- Error: Redirects to login.jsp with error=true

### Update Profile
**Endpoint:** `/UserServlet`
**Method:** POST

#### Parameters
| Name     | Type   | Required | Description         |
|----------|--------|----------|---------------------|
| action   | String | Yes      | Must be "updateProfile" |
| name     | String | No       | Updated name        |
| email    | String | Yes      | Updated email       |
| password | String | No       | New password        |
| address  | String | No       | Updated address     |

#### Response
- Success: Redirects to profile.jsp with updated=true
- Error: Redirects to login.jsp if session expired
