const { createProxyMiddleware } = require("http-proxy-middleware");

module.exports = function (app) {
  app.use(
    "/api/*",
    createProxyMiddleware({
      //http://localhost:8080
      target: "http://3.39.185.254:8080/",
      changeOrigin: true,
    })
  );
};
