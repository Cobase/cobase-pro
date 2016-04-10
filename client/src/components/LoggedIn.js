import React, { PropTypes } from 'react';
import ImmutablePropTypes from 'react-immutable-proptypes';
import Sidebar from './sidebar/Sidebar';

const LoggedIn = ({ children, groups, currentUser, onLogout }) => (
  <div className="logged-in">
    <Sidebar onLogout={onLogout} groups={groups} />

    {children && React.cloneElement(children, {
      currentUser,
    })}
  </div>
);

LoggedIn.propTypes = {
  children: PropTypes.element.isRequired,
  groups: ImmutablePropTypes.listOf(
    ImmutablePropTypes.contains({
      title: PropTypes.string.isRequired,
    })
  ),
  currentUser: ImmutablePropTypes.recordOf({
    id: PropTypes.string.isRequired,
    username: PropTypes.string.isRequired,
    role: PropTypes.string.isRequired,
    token: PropTypes.string.isRequired,
  }).isRequired,
  onLogout: PropTypes.func.isRequired,
};

export default LoggedIn;
