# 🗳️ Voting App Pro

A full-stack, multi-organization election and voting system built with **Spring Boot 3.5 + React 19**.  
Supports public and private elections, multi-position ballots, **Constituencies**, and **role-based administration** with a **Super User** who oversees all organizations.

---

## 🚀 Features
- Multi-tenant architecture (many organizations on one instance)
- **Super User**: manage all organizations and admins
- **Organization Admins / Viewers** with scoped permissions
- Create elections with **positions**, **candidates**, and **start/end** times
- Configure **Constituencies** → link eligible positions
- Generate **voter code batches** (scoped or global)
- **Private** or **Public** voting
- Live **turnout dashboard** (no results leakage)
- Auto-publish **results** at close
- Responsive UI (mobile, tablet, desktop)
- Deployable on our cloud or self-hosted

---

## 🧠 Role Hierarchy
| Role | Abilities |
|------|------------|
| **Super User** | Create/manage organizations, assign admins/viewers, view all elections & results |
| **Organization Admin** | Manage elections, candidates, codes, constituencies within one org |
| **Organization Viewer** | Read-only access to that org’s votes & results |
| **Voter** | Cast ballot (code required for private elections) |

---

## 🛠 Tech Stack
- **Backend:** Spring Boot 3.5 · Spring Security 6 · JPA/Hibernate · Flyway  
- **Frontend:** React 19 · Ant Design 5 · Vite  
- **Database:** PostgreSQL (recommended)  
- **Realtime:** Server-Sent Events (SSE)  
- **CI/CD:** GitHub Actions · Docker  

---

## ⚙️ Setup
### Clone
```bash
git clone https://github.com/yourname/voting-app-pro.git
cd voting-app-pro
