# ropu-module-events

## Routes

### **Get a single event by id**

---

**Request** `GET /<id>`

```json
{
  "id": id
}
```

**Response**

Succes:

```json
{
  "id": string,
  "date": "dd-MM-yyyy",
  "title": string,
  "description": string
}
```

Failure:

```
Unable to find this id
```

### **Get all events**

---

**Request** `GET /`

```json
//empty
```

**Response**

```json
{
    "current-date": "dd-mm-yyyy",
    "events": [
        index: {
            "id": string,
            "date": "dd-mm-yyyy",
        },
        ...
    ]
}
```

### **Update event**

---

**Request** `PUT /`

Success:

```json
{
  "id": string, //Required
  "date": "dd-MM-yyyy", //Optional
  "title": string, //Optional
  "description": string //Optional
}
```

Failure:

```

```
