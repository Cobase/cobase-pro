import React, { PropTypes } from 'react';

const LoggedOut = ({ children, onLogin }) => (
  <div className="logged-out">
    {children && React.cloneElement(children, {
      onLogin,
    })}
  </div>
);

LoggedOut.propTypes = {
  children: PropTypes.element.isRequired,
  onLogin: PropTypes.func.isRequired,
};

export default LoggedOut;
