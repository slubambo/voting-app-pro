import React from 'react';
import MyGroups from '../MyGroups/MyGroups';
import Notifications from './Notifications';

import GroupActions from './GroupActions';
import PollList from '../../poll/PollList';
import './HomeDashboard.css';

function HomeDashboard(props) {
  return (
    <div className="dashboard-container">
      <div className="top-section">
        <MyGroups currentUser={props.currentUser} />
        <Notifications />
      </div>
      <GroupActions />
      <div className="poll-list-wrapper">
        <PollList
          isAuthenticated={props.isAuthenticated}
          currentUser={props.currentUser}
          handleLogout={props.handleLogout}
          {...props}
        />
      </div>

    </div>
  );
}

export default HomeDashboard;
