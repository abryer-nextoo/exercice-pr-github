const express = require('express');
const path = require('path');
const os = require('os');
const fs = require('fs');
require('dotenv').config(); // Load dotenv

const app = express();

// Define the folder for static files for your front-end application
app.use(express.static(path.join(__dirname, 'public')));

app.get('/script.js', (req, res) => {
  const BASE_URL = process.env.BASE_URL;
  const PORT_FRONT = process.env.PORT_FRONT
  const PORT_BACK = process.env.PORT_BACK
  const scriptContent = `
    const BASE_URL = "${BASE_URL}";
    const PORT_FRONT = "${PORT_FRONT}";
    const PORT_BACK = "${PORT_BACK}";
    ${fs.readFileSync(path.join(__dirname, 'public/js/script.js'), 'utf8')}
  `;
  res.type('application/javascript').send(scriptContent); // Spécifier le type MIME comme application/javascript
});

app.get('/Event.js',(req,res) => {
  const BASE_URL = process.env.BASE_URL;
  const PORT_FRONT = process.env.PORT_FRONT
  const PORT_BACK = process.env.PORT_BACK
  const scriptContent = `
    const BASE_URL = "${BASE_URL}";
    const PORT_FRONT = "${PORT_FRONT}";
    const PORT_BACK = "${PORT_BACK}";
    ${fs.readFileSync(path.join(__dirname, 'public/js/Event.js'), 'utf8')}
  `;
  res.type('application/javascript').send(scriptContent); // Spécifier le type MIME comme application/javascript
})

// Start the server
const port = process.env.PORT_FRONT || 3000;
app.listen(port, '0.0.0.0', () => {
  const networkInterfaces = os.networkInterfaces();
  const addresses = [];
  for (let iface of Object.values(networkInterfaces)) {
    for (let alias of iface) {
      if (alias.family === 'IPv4' && !alias.internal) {
        addresses.push(alias.address);
      }
    }
  }
  console.log(`Server is running on:\n${addresses.map(addr => `http://${addr}:${port}`).join('\n')}`);
});
