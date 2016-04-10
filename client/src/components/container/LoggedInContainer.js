import React, { Component, PropTypes } from 'react';
import ImmutablePropTypes from 'react-immutable-proptypes';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';

import LoggedIn from '../LoggedIn';
import * as userActions from '../../actions/UserActions';
import * as groupActions from '../../actions/GroupActions';

class LoggedInContainer extends Component {
  constructor(props) {
    super(props);

    this.onLogout = this.onLogout.bind(this);
  }

  componentDidMount() {
    const { groupActions, currentUser } = this.props; // eslint-disable-line no-shadow

    groupActions.getGroups(currentUser.user);
  }

  onLogout() {
    const { userActions, currentUser } = this.props; // eslint-disable-line no-shadow

    userActions.logout(currentUser.user);
  }

  render() {
    const { children, currentUser, groups } = this.props;

    return (
      <LoggedIn
        children={children}
        currentUser={currentUser.user}
        onLogout={this.onLogout}
        groups={groups}
      />
    );
  }
}

export default connect(
  state => ({
    currentUser: state.authentication.currentUser,
    groups: state.groups.groups,
  }),
  dispatch => ({
    userActions: bindActionCreators(userActions, dispatch),
    groupActions: bindActionCreators(groupActions, dispatch),
  })
)(LoggedInContainer);

LoggedInContainer.propTypes = {
  groupActions: PropTypes.object.isRequired,
  userActions: PropTypes.object.isRequired,
  children: PropTypes.element.isRequired,
  currentUser: PropTypes.shape({
    isFetching: PropTypes.bool.isRequired,
    user: ImmutablePropTypes.recordOf({
      id: PropTypes.string.isRequired,
      username: PropTypes.string.isRequired,
      role: PropTypes.string.isRequired,
      token: PropTypes.string.isRequired,
    }).isRequired,
  }),
  groups: ImmutablePropTypes.listOf(
    ImmutablePropTypes.contains({
      title: PropTypes.string.isRequired,
    })
  ),
};
