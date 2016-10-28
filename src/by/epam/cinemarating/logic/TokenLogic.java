package by.epam.cinemarating.logic;

import by.epam.cinemarating.dao.TokenDAO;
import by.epam.cinemarating.database.ConnectionPool;
import by.epam.cinemarating.database.WrapperConnection;
import by.epam.cinemarating.entity.Token;
import by.epam.cinemarating.exception.DAOException;
import by.epam.cinemarating.exception.LogicException;

public class TokenLogic {
    public void insertToken(Token token) throws LogicException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        WrapperConnection connection = connectionPool.takeConnection().orElseThrow(LogicException::new);
        TokenDAO tokenDAO = new TokenDAO(connection);
        try {
            tokenDAO.insert(token);
        } catch (DAOException e) {
            throw new LogicException(e);
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
    }

    public boolean findToken(long userId, String token) throws LogicException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        WrapperConnection connection = connectionPool.takeConnection().orElseThrow(LogicException::new);
        TokenDAO tokenDAO = new TokenDAO(connection);
        try {
            return tokenDAO.findEntity(userId, token);
        } catch (DAOException e) {
            throw new LogicException(e);
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
    }
}
