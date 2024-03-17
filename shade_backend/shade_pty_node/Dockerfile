FROM node:16

WORKDIR /app

COPY package.json /app

RUN npm install

RUN curl -O -L https://fossies.org/linux/privat/sshpass-1.10.tar.gz && tar xvzf sshpass-1.10.tar.gz  \
    && cd sshpass-1.10 \
    && ./configure \
    && make install


COPY . /app

EXPOSE 3000

EXPOSE 3001

CMD [ "npm", "start" ]