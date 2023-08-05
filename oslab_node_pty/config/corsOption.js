const whitelist = [
    "*",
];

const corsOptions = {
    origin: (origin, callback) => {
        if (whitelist.indexOf(origin) !== -1) {
            callback(null, true);
        } else {
            callback(new Error("NOT allowed"));
        }
    },
    credentials: true,
};

module.exports = corsOptions;