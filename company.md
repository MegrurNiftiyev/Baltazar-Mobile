GET
/api/companies
Get a list of companies

Parameters
Cancel
Name	Description
serviceType
string
(query)

RENT_A_CAR
limit
integer
(query)
20
cursor
string
(query)
cursor
Execute
Clear
Responses
Curl

curl -X 'GET' \
  'https://baltazar-backend-kf2f.onrender.com/api/companies?serviceType=RENT_A_CAR&limit=20' \
  -H 'accept: */*'
Request URL
https://baltazar-backend-kf2f.onrender.com/api/companies?serviceType=RENT_A_CAR&limit=20
Server response
Code	Details
200	
Response body
Download
{
  "success": true,
  "data": [
    {
      "id": "FVPTgZNozCEwCWGYxWT2",
      "name": "Azerbaijan Luxury Drive",
      "about": "Sports and premium class car rental.",
      "serviceType": "RENT_A_CAR",
      "profileImage": "https://storage.googleapis.com/baltazar-a28a4.firebasestorage.app/rentacarCompanies%2Fdcf6d7a3-a5c1-4b5d-9962-52c7a55e0e68.jpg",
      "bannerImage": "https://storage.googleapis.com/baltazar-a28a4.firebasestorage.app/rentacarCompanies%2Fef6ef700-66ca-4b7b-9aad-74462728e5b0.jpg",
      "status": "ACTIVE",
      "sectionOrder": [
        "ABOUT",
        "ITEMS"
      ],
      "order": 0,
      "createdAt": "2026-08-16T13:16:04.252Z",
      "relatedItemIds": [
        "IXnFdfU0oDV3QnmXHZls",
        "EQ4JJbtcOz7U0NVAMuq6"
      ],
      "reviewCount": 2,
      "ratingSum": 9,
      "rating": 4.5
    },
    {
      "id": "kCHpaUm5HCHnD2ofYTH9",
      "name": "VIP Express Car Rental",
      "about": "VIP vehicles and personal chauffeur services.",
      "serviceType": "RENT_A_CAR",
      "profileImage": "https://storage.googleapis.com/baltazar-a28a4.firebasestorage.app/rentacarCompanies%2F5ba59233-6249-46bd-b25c-ae348c111794.jpg",
      "bannerImage": "https://storage.googleapis.com/baltazar-a28a4.firebasestorage.app/rentacarCompanies%2F29be49c6-227c-4dec-89ca-90d18ed6b401.jpg",
      "status": "ACTIVE",
      "sectionOrder": [
        "GALLERY"
      ],
      "order": 0,
      "createdAt": "2026-08-16T13:16:02.582Z",
      "relatedItemIds": [
        "2Z01jn4k3zmC8zcwy5d2",
        "DtxR34kHem1zfGjy2XsT",
        "BcQ73wzFiFj3JGVqubP8",
        "8XlBmtTccrA9D4X0vERw"
      ],
      "reviewCount": 3,
      "ratingSum": 13,
      "rating": 4.33
    },
    {
      "id": "5F5y9pGD1nv3BS6Fd9i6",
      "name": "Caspian Car Hire",
      "about": "Reliable rental service with affordable rates.",
      "serviceType": "RENT_A_CAR",
      "profileImage": "https://storage.googleapis.com/baltazar-a28a4.firebasestorage.app/rentacarCompanies%2F25cd1f20-8fb8-4804-a622-beb83f040bc8.jpg",
      "bannerImage": "https://storage.googleapis.com/baltazar-a28a4.firebasestorage.app/rentacarCompanies%2Ff677ee17-e025-4d29-9a8e-85b95961edef.jpg",
      "status": "ACTIVE",
      "sectionOrder": [
        "ITEMS",
        "ABOUT"
      ],
      "order": 0,
      "createdAt": "2026-08-16T13:16:00.901Z",
      "relatedItemIds": [
        "7z7b4UczBlXrKDAqZa2u",
        "XUYZAt5Kj7PhxfUld9VH",
        "iFCtT8EQ7JaAPYD2GsLa"
      ],
      "reviewCount": 2,
      "ratingSum": 7,
      "rating": 3.5
    },
    {
      "id": "k7sRCvP37vaMNesMeHoE",
      "name": "Baku Auto Rental",
      "about": "Luxury and economy car rentals in Baku.",
      "serviceType": "RENT_A_CAR",
      "profileImage": "https://storage.googleapis.com/baltazar-a28a4.firebasestorage.app/rentacarCompanies%2Fbf7fb19f-4425-4e63-9c3e-92348acf9af3.jpg",
      "bannerImage": "https://storage.googleapis.com/baltazar-a28a4.firebasestorage.app/rentacarCompanies%2F21ef9cd3-1f87-4cd2-96ec-a894dc1153f9.jpg",
      "status": "ACTIVE",
      "sectionOrder": [
        "ABOUT",
        "GALLERY",
        "ITEMS"
      ],
      "order": 0,
      "rating": 5,
      "createdAt": "2026-08-16T13:15:59.269Z",
      "relatedItemIds": [
        "jiQWiGZCtNLjAgkOAS7T",
        "t3MCP40XRQCtnEXQtWbH",
        "FchBrgeYxm9kcFhFHw36",
        "l7FuaBsG2ElqO0gS0oPj"
      ],
      "reviewCount": 3,
      "ratingSum": 15
    }
  ],
  "pagination": {
    "nextCursor": null,
    "hasMore": false,
    "limit": 20
  }
}
Response headers
 access-control-allow-credentials: true 
 alt-svc: h3=":443"; ma=86400 
 cf-cache-status: DYNAMIC 
 cf-ray: a2c91ebc69ce8ec8-GYD 
 content-encoding: br 
 content-length: 1110 
 content-security-policy: default-src 'self';base-uri 'self';font-src 'self' https: data:;form-action 'self';frame-ancestors 'self';img-src 'self' data:;object-src 'none';script-src 'self';script-src-attr 'none';style-src 'self' https: 'unsafe-inline';upgrade-insecure-requests 
 content-type: application/json; charset=utf-8 
 cross-origin-opener-policy: same-origin 
 cross-origin-resource-policy: same-origin 
 date: Mon,17 Aug 2026 13:45:37 GMT 
 etag: W/"ab4-dHJjQPhUO+GbRUe0XvMaRy+RgvU" 
 origin-agent-cluster: ?1 
 referrer-policy: no-referrer 
 rndr-id: 4339ff2f-515a-47d6 
 server: cloudflare 
 strict-transport-security: max-age=31536000; includeSubDomains 
 vary: Origin,Accept-Encoding 
 x-content-type-options: nosniff 
 x-dns-prefetch-control: off 
 x-download-options: noopen 
 x-frame-options: SAMEORIGIN 
 x-permitted-cross-domain-policies: none 
 x-render-origin-server: Render 
 x-xss-protection: 0 
Responses
Code	Description	Links
200	
List of companies

No links

POST
/api/companies
Create a new company



GET
/api/companies/{id}/related-items
Get resolved related items for a company as cross-service DTO cards

Parameters
Cancel
Name	Description
id *
string
(path)
FVPTgZNozCEwCWGYxWT2
Execute
Clear
Responses
Curl

curl -X 'GET' \
  'https://baltazar-backend-kf2f.onrender.com/api/companies/FVPTgZNozCEwCWGYxWT2/related-items' \
  -H 'accept: */*'
Request URL
https://baltazar-backend-kf2f.onrender.com/api/companies/FVPTgZNozCEwCWGYxWT2/related-items
Server response
Code	Details
200	
Response body
Download
{
  "success": true,
  "data": [
    {
      "id": "IXnFdfU0oDV3QnmXHZls",
      "serviceType": "RENT_A_CAR",
      "serviceId": "IXnFdfU0oDV3QnmXHZls",
      "title": "Porsche 911 Carrera S",
      "image": "https://storage.googleapis.com/baltazar-a28a4.firebasestorage.app/rentacarCars%2F3ff9120e-8ca2-41e8-bcee-8eb0b3aba4b2.png",
      "price": 600,
      "priceSuffix": "/ day",
      "currency": "USD",
      "rating": 4.67,
      "ratingCount": 3,
      "category": "Lüks"
    },
    {
      "id": "EQ4JJbtcOz7U0NVAMuq6",
      "serviceType": "RENT_A_CAR",
      "serviceId": "EQ4JJbtcOz7U0NVAMuq6",
      "title": "Tesla Model S Plaid",
      "image": "https://storage.googleapis.com/baltazar-a28a4.firebasestorage.app/rentacarCars%2F37762708-90ab-45a8-ad4b-8d0f1be948a5.png",
      "price": 400,
      "priceSuffix": "/ day",
      "currency": "USD",
      "rating": 4.33,
      "ratingCount": 3,
      "category": "Elektrikli"
    }
  ]
}
Response headers
 access-control-allow-credentials: true 
 alt-svc: h3=":443"; ma=86400 
 cf-cache-status: DYNAMIC 
 cf-ray: a2c91ffb8b998ec8-GYD 
 content-encoding: br 
 content-length: 363 
 content-security-policy: default-src 'self';base-uri 'self';font-src 'self' https: data:;form-action 'self';frame-ancestors 'self';img-src 'self' data:;object-src 'none';script-src 'self';script-src-attr 'none';style-src 'self' https: 'unsafe-inline';upgrade-insecure-requests 
 content-type: application/json; charset=utf-8 
 cross-origin-opener-policy: same-origin 
 cross-origin-resource-policy: same-origin 
 date: Mon,17 Aug 2026 13:46:27 GMT 
 etag: W/"2e4-0p6BurSD/CApNnmacT96DMqtbac" 
 origin-agent-cluster: ?1 
 priority: u=1,i 
 referrer-policy: no-referrer 
 rndr-id: 5f5d64ab-bf01-431b 
 server: cloudflare 
 server-timing: cfExtPri 
 strict-transport-security: max-age=31536000; includeSubDomains 
 vary: Origin 
 x-content-type-options: nosniff 
 x-dns-prefetch-control: off 
 x-download-options: noopen 
 x-frame-options: SAMEORIGIN 
 x-permitted-cross-domain-policies: none 
 x-render-origin-server: Render 
 x-xss-protection: 0 
Responses
Code	Description	Links
200	
List of related item cards

No links
404	
Company not found

No links

GET
/api/companies/{id}
Get a company by ID

Parameters
Cancel
Name	Description
id *
string
(path)
FVPTgZNozCEwCWGYxWT2
Execute
Clear
Responses
Curl

curl -X 'GET' \
  'https://baltazar-backend-kf2f.onrender.com/api/companies/FVPTgZNozCEwCWGYxWT2' \
  -H 'accept: */*'
Request URL
https://baltazar-backend-kf2f.onrender.com/api/companies/FVPTgZNozCEwCWGYxWT2
Server response
Code	Details
200	
Response body
Download
{
  "success": true,
  "data": {
    "id": "FVPTgZNozCEwCWGYxWT2",
    "name": "Azerbaijan Luxury Drive",
    "about": "Sports and premium class car rental.",
    "serviceType": "RENT_A_CAR",
    "profileImage": "https://storage.googleapis.com/baltazar-a28a4.firebasestorage.app/rentacarCompanies%2Fdcf6d7a3-a5c1-4b5d-9962-52c7a55e0e68.jpg",
    "bannerImage": "https://storage.googleapis.com/baltazar-a28a4.firebasestorage.app/rentacarCompanies%2Fef6ef700-66ca-4b7b-9aad-74462728e5b0.jpg",
    "status": "ACTIVE",
    "sectionOrder": [
      "ABOUT",
      "ITEMS"
    ],
    "order": 0,
    "createdAt": "2026-08-16T13:16:04.252Z",
    "relatedItemIds": [
      "IXnFdfU0oDV3QnmXHZls",
      "EQ4JJbtcOz7U0NVAMuq6"
    ],
    "reviewCount": 2,
    "rating": 4.5,
    "fullSectionOrder": [
      "HEADER",
      "ABOUT",
      "ITEMS",
      "REVIEWS"
    ],
    "reviewEligibility": {
      "eligible": false,
      "alreadyReviewed": false,
      "canSubmit": false
    }
  }
}
Response headers
 access-control-allow-credentials: true 
 alt-svc: h3=":443"; ma=86400 
 cf-cache-status: DYNAMIC 
 cf-ray: a2c91ee088898ec8-GYD 
 content-encoding: br 
 content-length: 500 
 content-security-policy: default-src 'self';base-uri 'self';font-src 'self' https: data:;form-action 'self';frame-ancestors 'self';img-src 'self' data:;object-src 'none';script-src 'self';script-src-attr 'none';style-src 'self' https: 'unsafe-inline';upgrade-insecure-requests 
 content-type: application/json; charset=utf-8 
 cross-origin-opener-policy: same-origin 
 cross-origin-resource-policy: same-origin 
 date: Mon,17 Aug 2026 13:45:41 GMT 
 etag: W/"30a-5NDbFhTltG5NygQoBYZlIaIWIyY" 
 origin-agent-cluster: ?1 
 priority: u=1,i 
 referrer-policy: no-referrer 
 rndr-id: 24835fdc-b03c-4f80 
 server: cloudflare 
 server-timing: cfExtPri 
 strict-transport-security: max-age=31536000; includeSubDomains 
 vary: Origin 
 x-content-type-options: nosniff 
 x-dns-prefetch-control: off 
 x-download-options: noopen 
 x-frame-options: SAMEORIGIN 
 x-permitted-cross-domain-policies: none 
 x-render-origin-server: Render 
 x-xss-protection: 0 
Responses
Code	Description	Links
200	
Company details

No links
404	
Company not found